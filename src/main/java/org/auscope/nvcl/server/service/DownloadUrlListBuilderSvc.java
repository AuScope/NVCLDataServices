package org.auscope.nvcl.server.service;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.auscope.nvcl.server.vo.ConfigVo;
import org.auscope.nvcl.server.vo.DatasetCollectionVo;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class DownloadUrlListBuilderSvc  {

	private static final Logger logger = LogManager.getLogger(CleanUpDownloadFolderJob.class);


	@Autowired
	@Qualifier(value = "nvclDataSvc")
	private NVCLDataSvc nvclDataSvc;

	@Autowired
	@Qualifier(value = "nvclDownloadSvc")
	private NVCLDownloadSvc nvclDownloadSvc;


	@Autowired
	@Qualifier(value = "createConfig")
	private ConfigVo config;


	@Scheduled(fixedRate = 3,timeUnit = TimeUnit.DAYS)
	protected void executeInternal() throws JobExecutionException {
        logger.debug("begin refresh of cached download links for all datasets");
        DatasetCollectionVo alldatasets = nvclDataSvc.getDatasetCollection("all");
        nvclDownloadSvc.buildDownloadUrlHasMap(alldatasets);
        logger.debug("Completed refresh of cached download links for all datasets");
		
	}


}