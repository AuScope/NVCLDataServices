package org.auscope.nvcl.server.service;

import java.io.File;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CleanUpDownloadFolderJob extends QuartzJobBean {
	
	private static final Logger logger = LogManager.getLogger(CleanUpDownloadFolderJob.class);

	private long days;
	private String downloadfolderpath;
	  
	public void setDays(long days) {
	  this.days = days;
	}
	
	public void setDownloadfolderpath(String downloadfolderpath) {
		  this.downloadfolderpath = downloadfolderpath;
		}
	  
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		int filescleaned=0;
		logger.debug("Download Folder cleaner running");
		File Folder = new File(this.downloadfolderpath);
		if(Folder.exists()){
			for(File listFile : Folder.listFiles()) {
				if(listFile.getName().toLowerCase().endsWith(".zip") && (listFile.lastModified() < System.currentTimeMillis() - (days*86400000))) {
					listFile.delete();
					filescleaned++;
				}
			}
		}
		logger.debug("Download Folder cleaner complete, " + filescleaned + " file(s) deleted.");
		
	}

}
