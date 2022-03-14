package org.auscope.nvcl.server.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.auscope.nvcl.server.vo.ConfigVo;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

	@Autowired
	@Qualifier(value = "createConfig")
	private ConfigVo config;

	@Scheduled(cron="0 */10 * * * ?")
	protected void executeInternal() throws JobExecutionException {
		int filescleaned = 0;
		logger.debug("Download Folder cleaner running on download folder " + this.downloadfolderpath);
		File downloadsFolder = new File(this.downloadfolderpath);
        long usablespace = downloadsFolder.getUsableSpace(); 
		logger.debug("usable disk space is "+usablespace/(1000000000)+"Gb.");
		if ( usablespace<10000000000L ) {
			if (downloadsFolder.exists()) {
				File[] files = downloadsFolder.listFiles();
				Arrays.sort(files, Comparator.comparingLong(File::lastModified));
				for (File listFile : files) {
					logger.debug("checking :"+listFile.getName());
					if (listFile.getName().toLowerCase().endsWith(".zip")) {
						if (this.config.getAggressiveCacheClearing() || listFile.lastModified() < System.currentTimeMillis() - (days * 86400000)) {
							if (listFile.lastModified() > System.currentTimeMillis() - (days * 86400000)) {
								logger.warn("WARNING: Aggressive Cache Clearing is deleting a file before the user's time to download it has expired.  This may annoy users.  Increase the size of your cache!");
							}
							logger.debug("deleting file :" + listFile.getName());
							try{
								listFile.delete();
							}
							catch (Exception e) {
								logger.warn("couldnt delete file.  probably an IO issue.  Ignore for now and try again later.");
							}
							filescleaned++;
						}
					}
        			long newusablespace = downloadsFolder.getUsableSpace();
					if ( newusablespace>10000000000L ) {
						logger.info("Cache cleaner has clean enough files.  Usable space is now : "+newusablespace);
						break;
					}
				}
			}
		}
		else {
			logger.debug("cleanup skipped as sufficent disk space remains");
		}
		logger.debug("Download Folder cleaner complete, " + filescleaned + " file(s) deleted.");

		logger.debug("Cache Folder cleaner running on cache folder " + this.cachefolderpath);
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
