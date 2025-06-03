package org.auscope.nvcl.server.service;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.azure.core.credential.TokenCredential;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
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

}
