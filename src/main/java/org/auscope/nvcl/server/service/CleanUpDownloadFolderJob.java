package org.auscope.nvcl.server.service;

import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CleanUpDownloadFolderJob extends QuartzJobBean {

	private static final Logger logger = LogManager.getLogger(CleanUpDownloadFolderJob.class);

	private long days;
	private String downloadfolderpath;
	private String cachefolderpath;

	public void setCachefolderpath(String cachefolderpath) {
		this.cachefolderpath = cachefolderpath;
	}

	public void setDays(long days) {
		this.days = days;
	}

	public void setDownloadfolderpath(String downloadfolderpath) {
		this.downloadfolderpath = downloadfolderpath;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		int filescleaned = 0;
		logger.debug("Download Folder cleaner running");
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
