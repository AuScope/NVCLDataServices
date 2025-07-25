package org.auscope.nvcl.server.service;

import java.io.File;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.azure.core.credential.TokenCredential;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobProperties;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.identity.EnvironmentCredentialBuilder;
import com.azure.identity.AzureCliCredentialBuilder;
import com.azure.identity.ChainedTokenCredentialBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.auscope.nvcl.server.util.Utility;
import org.auscope.nvcl.server.vo.ConfigVo;
import org.auscope.nvcl.server.vo.ImageDataVo;
import org.auscope.nvcl.server.vo.SpectralDataCollectionVo;
import org.auscope.nvcl.server.vo.SpectralDataVo;


public class NVCLBlobStoreAccessSvc {
    private static final Logger logger = LogManager.getLogger(NVCLBlobStoreAccessSvc.class);
    public boolean isConnected = false;
    private ConfigVo config;
	public void setConfig(ConfigVo config) {
			this.config = config;
	}

    private BlobServiceClient blobServiceClient;

    public NVCLBlobStoreAccessSvc(ConfigVo config) {
        this.setConfig(config);
        if (!Utility.stringIsBlankorNull(config.getAzureStorageEndPoint())) {
            TokenCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder().build();
            TokenCredential environmentCredential = new EnvironmentCredentialBuilder().build();
            TokenCredential azureCliCredential = new AzureCliCredentialBuilder().build();
            TokenCredential credential = new ChainedTokenCredentialBuilder()
                .addLast(managedIdentityCredential)
                .addLast(environmentCredential)
                .addLast(azureCliCredential)
                .build();
            this.blobServiceClient = new BlobServiceClientBuilder().endpoint(config.getAzureStorageEndPoint()).credential(credential).buildClient();
            this.isConnected=true;
        }
        else if (!Utility.stringIsBlankorNull(this.config.getAzureBlobStoreConnectionString())) {
            this.blobServiceClient = new BlobServiceClientBuilder().endpoint(this.config.getAzureBlobStoreConnectionString()).buildClient();
            this.isConnected=true;
        }

    }
    
    public ImageDataVo getImgData(String datasetid, String imagelogid, int sampleno) {

        String containerName = this.config.getAzureContainerName();
        
        String blobName = "tsgdataset-" + datasetid+"/imageLogData/"+imagelogid+"/"+sampleno+".jpg";

        BlockBlobClient blobClient = this.blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobName).getBlockBlobClient();
        
        ImageDataVo imageDataVo = new ImageDataVo();
        if ( blobClient.exists()) {
            imageDataVo.setImgData(blobClient.downloadContent().toBytes());
        }
        
        return imageDataVo;
    }

    public SpectralDataCollectionVo getSpectralData(String datasetid, String speclogid, int startsampleno, int endsampleno) {
        String containerName = this.config.getAzureContainerName();
        ArrayList<SpectralDataVo> spectralist = new ArrayList<SpectralDataVo>();
        for (int i=startsampleno;i<=endsampleno;i++){

            String blobName = "tsgdataset-" + datasetid+"/spectralLogData/"+speclogid+"/"+i+".bin";
            
            BlockBlobClient blobClient = this.blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobName).getBlockBlobClient();
            
            SpectralDataVo spectralData = new SpectralDataVo();
            spectralData.setSampleNo(i);
            if ( blobClient.exists()) {
                spectralData.setSpectraldata(blobClient.downloadContent().toBytes());
            }
            spectralist.add(spectralData);
        }
        return new SpectralDataCollectionVo(spectralist);
    }

    public int getSpectralDataCount(String datasetid, String speclogid) {
        String containerName = this.config.getAzureContainerName();

        String blobName = "tsgdataset-" + datasetid+"/spectralLogData/"+speclogid+'/';
        //int count=0;
        AtomicInteger count = new AtomicInteger();
        this.blobServiceClient.getBlobContainerClient(containerName).listBlobsByHierarchy(blobName).forEach(blob -> count.getAndIncrement());
        logger.debug("speci id is "+ speclogid + " count is "+ count.get());
        return count.get();
    }

    public Boolean blobExists(String blobName,String containerName,long modifieddate) {

        BlockBlobClient blobClient = this.blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobName).getBlockBlobClient();
        
        OffsetDateTime dateTime = Instant.ofEpochMilli(modifieddate).atOffset(ZoneOffset.UTC);
        
        if ( blobClient.exists() && blobClient.getProperties().getLastModified().isAfter(dateTime)) {

            return true;
        }
        
        return false;
    }

    public void UploadTSGFileBundletoAzureBlobContainer(String blobName,String containerName, File localFile) {

        BlobClient blobClient = this.blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobName);

        blobClient.uploadFromFile(localFile.getAbsolutePath(), true); // true = overwrite if exists

    }

    public void touchBlob(String blobName,String containerName) {

        BlockBlobClient blobClient = this.blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobName).getBlockBlobClient();
        
        if ( blobClient.exists()) {
            
            Map<String, String> metadata = new HashMap<>();
            metadata.put("updated", String.valueOf(System.currentTimeMillis()));

            blobClient.setMetadata(metadata);
        }
    }

    public int cleanupoldestblobsinAzureContainer(int daysToKeep, String containerName, int maxGBstoRetain){
        OffsetDateTime cutoffDate = OffsetDateTime.now().minusDays(daysToKeep); 

        BlobContainerClient containerClient = this.blobServiceClient.getBlobContainerClient(containerName);

        
        long totalSize = 0;
        long maxSizeinBytes = (long)maxGBstoRetain*1024L*1024L*1024L;

        for (BlobItem blobItem : containerClient.listBlobs()) {
            BlobClient blobClient = containerClient.getBlobClient(blobItem.getName());
            BlobProperties properties = blobClient.getProperties();
            totalSize += properties.getBlobSize();
        }
        
        int blobscleaned=0;

        if (totalSize>maxSizeinBytes) {
            logger.warn("Blob storage size "+ (totalSize/(1024L*1024L*1024L)) +"GB has exceeded the maximum "+maxGBstoRetain+"GB.  The oldest files will now be removed until the total size falls bellow the limit");
            List<BlobItem> blobList = new ArrayList<>();
            containerClient.listBlobs().forEach(blobList::add);

            // Sort blobs by LastModified date (oldest to newest)
            blobList.sort(Comparator.comparing(blob -> blob.getProperties().getLastModified()));


            for (BlobItem blobItem : blobList) {
                if (blobItem.getProperties().getLastModified().isBefore(cutoffDate) && blobItem.getName().endsWith(".zip") ) {
                    String blobName = blobItem.getName();
                    totalSize -= blobItem.getProperties().getContentLength();
                    containerClient.getBlobClient(blobName).delete();
                    logger.debug(" - " + blobName + " (Modified: " + blobItem.getProperties().getLastModified() + ") DELETED");
                    blobscleaned++;
                    if (totalSize<maxSizeinBytes) break;
                }
            }
        }
        return blobscleaned;
    }

}
