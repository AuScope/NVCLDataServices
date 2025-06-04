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

	@Autowired
	@Qualifier(value = "createConfig")
	private ConfigVo config;

	@Scheduled(cron="0 */10 * * * ?")
	protected void executeInternal() throws JobExecutionException {
		int filescleaned = 0;
		long days = this.config.getMsgTimetoLiveDays();
		String cachefolderpath = this.config.getDownloadCachePath();
		String downloadsfolderpath=this.config.getDownloadRootPath();
		long minDiskspaceinBytes = ((long)this.config.getMinDiskspace())*1000000000L;
		logger.debug("Download Folder cleaner running on download folder " + downloadsfolderpath);
		File downloadsFolder = new File(downloadsfolderpath);
        long usablespace = downloadsFolder.getUsableSpace(); 
		logger.debug("usable disk space is "+usablespace/(1000000000)+"Gb.");
		if ( usablespace<minDiskspaceinBytes ) {
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
					if ( newusablespace>minDiskspaceinBytes ) {
						logger.debug("Cache cleaner has clean enough files.  Usable space is now : "+newusablespace);
						break;
					}
				}
			}
		}
		else {
			logger.debug("cleanup skipped as sufficent disk space remains");
		}
		logger.debug("Download Folder cleaner complete, " + filescleaned + " file(s) deleted.");

		logger.debug("Cache Folder cleaner running on cache folder " + cachefolderpath);
		int cachefolderscleaned = 0;
		File cacheFolder = new File(cachefolderpath);
		if (cacheFolder.exists()) {
			File[] CachelistFiles = cacheFolder.listFiles();
			Arrays.sort(CachelistFiles, Comparator.comparingLong(File::lastModified));
			// clean all but the most recent folder incase its still being processed
			for (int i=0; i <CachelistFiles.length-1;i++) {
				File CachelistFile =CachelistFiles[i];
				if (CachelistFile.isDirectory()) {
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
