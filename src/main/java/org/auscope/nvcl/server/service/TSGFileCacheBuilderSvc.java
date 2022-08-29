package org.auscope.nvcl.server.service;

import java.io.File;

import javax.jms.Destination;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.auscope.nvcl.server.util.Utility;
import org.auscope.nvcl.server.vo.ConfigVo;
import org.auscope.nvcl.server.vo.DatasetCollectionVo;
import org.auscope.nvcl.server.vo.DatasetVo;
import org.auscope.nvcl.server.vo.MessageVo;
import org.auscope.nvcl.server.vo.TsgParamVo;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TSGFileCacheBuilderSvc  {

	private static final Logger logger = LogManager.getLogger(CleanUpDownloadFolderJob.class);

	private String downloadfolderpath;

	@Autowired
	@Qualifier(value = "tsgRequestDestination")
	private Destination tsgReqDestination;

	@Autowired
	@Qualifier(value = "tsgReplyDestination")
	private Destination tsgReplyDestination;

	@Autowired
	@Qualifier(value = "jmsTemplate")
	private JmsTemplate jmsTemplate;

	@Autowired
	@Qualifier(value = "nvclDataSvc")
	private NVCLDataSvc nvclDataSvc;

	@Autowired
	@Qualifier(value = "nvclDownloadSvc")
	private NVCLDownloadSvc nvclDownloadSvc;

	@Autowired
	@Qualifier(value = "nvclDownloadGateway")
	private NVCLDownloadGateway nvclDownloadGateway;


    @Value("${download.rootpath}")
	public void setDownloadfolderpath(String downloadfolderpath) {
		this.downloadfolderpath = downloadfolderpath;
	}

	@Autowired
	@Qualifier(value = "createConfig")
	private ConfigVo config;


	@Scheduled(cron="0 */20 * * * ?")
	protected void executeInternal() throws JobExecutionException {
        if (this.config.getAutoCacheBuilder()) {
            logger.debug("Running TSG File Cache Builder " );
            DatasetCollectionVo datasetsindb = this.nvclDataSvc.getDatasetCollection("all");
            File downloadsFolder = new File(this.downloadfolderpath);
            long usablespace = downloadsFolder.getUsableSpace(); 
            NVCLDownloadQueueBrowser nvclDownloadQueueBrowser = new NVCLDownloadQueueBrowser();
            nvclDownloadQueueBrowser.setJmsTemplate(jmsTemplate);
            int itemsalreadyinthequeue = nvclDownloadQueueBrowser.getMessageCount(tsgReqDestination);
            if ( usablespace>20000000000L ) {
                if ( itemsalreadyinthequeue < 5) {
                    logger.debug("usable disk space is "+usablespace/(1000000000)+"Gb. As space is available building cache." );
                    if (downloadsFolder.exists()) {
                        int datasetsaddedcount=0;
                        for(DatasetVo dataset : datasetsindb.getDatasetCollection()) {
                            boolean datasetalreadycached = new File(downloadsFolder, dataset.getDatasetID()+".zip").exists();
                            boolean cachedonMirror = !Utility.stringIsBlankorNull(config.getDownloadFileMirror()) && !Utility.stringIsBlankorNull(this.nvclDownloadSvc.findDatasetInMirror(dataset.getDatasetID(), dataset.getDatasetName(), dataset.getModifiedDate().getTime()));
                            if (!datasetalreadycached && !cachedonMirror && !this.config.getAutoCacheFailedDatasetsList().contains(dataset.getDatasetID())) {
                                TsgParamVo tsgParamVo = new TsgParamVo();
                                MessageVo tsgreqmessage = new MessageVo();

                                tsgreqmessage.setAutoCacheJob(true);

                                // Set url parameters to URLParamVo object
                                tsgParamVo.setDatasetid(dataset.getDatasetID());

                                tsgParamVo.setEmail("peter.warren@csiro.au");
                                tsgreqmessage.setRequestorEmail("peter.warren@csiro.au");

                                logger.debug("Start generating script file...");

                                String scriptFileNameNoExt = nvclDownloadSvc.createScriptFile(tsgParamVo);

                                if (scriptFileNameNoExt.equals("fail")) {
                                    String errMsg = "Error occured while creating script file";
                                    logger.error(errMsg);
                                }
                                tsgreqmessage.setScriptFileNameNoExt(scriptFileNameNoExt);
                                tsgreqmessage.settSGDatasetID(dataset.getDatasetID());
                                tsgreqmessage.setDatasetname(dataset.getDatasetName());
                                tsgreqmessage.setDbModifiedDate(dataset.getModifiedDate().getTime());
                                String boreholeURI = nvclDataSvc.getBoreholeHoleURIbyDatasetId(dataset.getDatasetID());
                                tsgreqmessage.setBoreholeid(boreholeURI);

                                logger.debug("Script file created successfully ...");

                                logger.debug("Start create JMS message ...");
                                nvclDownloadGateway.setDestination(tsgReqDestination);
                                String messageID = nvclDownloadGateway.createTSGDownloadReqMsg(tsgreqmessage);

                                if (messageID == null) {
                                    String errMsg = "Failed to enqueue job";
                                    logger.error(errMsg);
                                }
                                datasetsaddedcount++;
                                if (datasetsaddedcount+itemsalreadyinthequeue>=5) break;
                            }

                        }
                    }
                }
                else {
                    logger.debug("Build Cache Job skipped because jobs are already in the queue");
                }
            }
            else {
                logger.debug("Build Cache Job skipped because insufficent disk space is available");
            }
		}
		
	}

    @Scheduled(cron="0 0 2 * * ?")
	protected void CleanoutdatedFiles() throws JobExecutionException {
        File downloadsFolder = new File(this.downloadfolderpath);
        logger.debug("running outdated file cleaner");
        if (downloadsFolder.exists()) {
            for (final File fileEntry : downloadsFolder.listFiles()) {
                if (!fileEntry.isDirectory()) {
                    DatasetCollectionVo dataset = nvclDataSvc.getDatasetCollectionbyDatasetId(fileEntry.getName().replace(".zip",""));
                    if (dataset.getDatasetCollection().size()==1 && dataset.getDatasetCollection().get(0).getModifiedDate().getTime()>fileEntry.lastModified()){
                        fileEntry.delete();
                        logger.info("deleted file "+fileEntry.getName() + " because it was created before the last modified date for this dataset");
                    }
                } 
            }
        }
    }

}