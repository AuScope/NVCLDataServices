package org.auscope.nvcl.server.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CleanUpDownloadFolderJob {

	private static final Logger logger = LogManager.getLogger(CleanUpDownloadFolderJob.class);

	private long days;
	private String downloadfolderpath;
	private String cachefolderpath;

    @Value("${download.cachepath}")
	public void setCachefolderpath(String cachefolderpath) {
		this.cachefolderpath = cachefolderpath;
	}
	
    @Value("${msgTimetoLiveDays}") 
	public void setDays(long days) {
		this.days = days;
	}

    @Value("${download.rootpath}")
	public void setDownloadfolderpath(String downloadfolderpath) {
		this.downloadfolderpath = downloadfolderpath;
	}


	@Scheduled(cron="0 0 1 * * ?")
	protected void executeInternal() throws JobExecutionException {
		int filescleaned = 0;
		logger.info("Download Folder cleaner running" + this.cachefolderpath + "   " + this.days + "   " + this.downloadfolderpath);
		File downloadsFolder = new File(this.downloadfolderpath);
		if (downloadsFolder.exists()) {
			for (File listFile : downloadsFolder.listFiles()) {
				if (listFile.getName().toLowerCase().endsWith(".zip")
						&& (listFile.lastModified() < System.currentTimeMillis() - (days * 86400000))) {
					listFile.delete();
					filescleaned++;
				}
			}
		}
		logger.debug("Download Folder cleaner complete, " + filescleaned + " file(s) deleted.");

		logger.debug("Cache Folder cleaner running");
		int cachefolderscleaned = 0;
		File cacheFolder = new File(this.cachefolderpath);
		if (cacheFolder.exists()) {
			for (File CachelistFile : cacheFolder.listFiles()) {
				if (CachelistFile.isDirectory()
						&& (CachelistFile.lastModified() < System.currentTimeMillis() - (days * 86400000))) {
					try {
						FileUtils.deleteDirectory(CachelistFile);
						cachefolderscleaned++;
					} catch (IOException e) {
						// attempt to clean up but ignore if it fails.
						logger.debug("cleanup cache folder " + CachelistFile + " failed");
					}
				}
			}
		}
		logger.debug("Cache Folder cleaner complete, " + cachefolderscleaned + " folder(s) deleted.");
	}

}
