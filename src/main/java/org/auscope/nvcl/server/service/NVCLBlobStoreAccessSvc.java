package org.auscope.nvcl.server.service;

import java.util.ArrayList;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.auscope.nvcl.server.vo.ConfigVo;
import org.auscope.nvcl.server.vo.ImageDataVo;
import org.auscope.nvcl.server.vo.SpectralDataCollectionVo;
import org.auscope.nvcl.server.vo.SpectralDataVo;


public class NVCLBlobStoreAccessSvc {
    private static final Logger logger = LogManager.getLogger(NVCLBlobStoreAccessSvc.class);

    private ConfigVo config;
	public void setConfig(ConfigVo config) {
			this.config = config;
	}

    private BlobServiceClient blobServiceClient;

    public NVCLBlobStoreAccessSvc(ConfigVo config) {
        this.setConfig(config);
        this.blobServiceClient = new BlobServiceClientBuilder().connectionString(this.config.getAzureBlobStoreConnectionString()).buildClient();

    }
    
    public ImageDataVo getImgData(String datasetid, String imagelogid, int sampleno) {

        String containerName = "tsgdataset-" + datasetid;

        String blobName = "imageLogData\\"+imagelogid+"\\"+sampleno+".jpg";

        BlockBlobClient blobClient = this.blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobName).getBlockBlobClient();
        
        ImageDataVo imageDataVo = new ImageDataVo();
        if ( blobClient.exists()) {
            imageDataVo.setImgData(blobClient.downloadContent().toBytes());
        }
        
        return imageDataVo;
    }

    public SpectralDataCollectionVo getSpectralData(String datasetid, String speclogid, int startsampleno, int endsampleno) {
        String containerName = "tsgdataset-" + datasetid;
        ArrayList<SpectralDataVo> spectralist = new ArrayList<SpectralDataVo>();
        for (int i=startsampleno;i<=endsampleno;i++){

            String blobName = "spectralLogData\\"+speclogid+"\\"+i+".bin";
            
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

}
