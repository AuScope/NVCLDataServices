package org.auscope.nvcl.server.vo;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.auscope.nvcl.server.service.TSGDownloadRequestSvc;
import org.auscope.nvcl.server.util.Utility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This Config Value Object class set the configuration info from config.properties 
 * and allow getting the values thru getter and setter method.
 * 
 * @author Florence Tan
 */

@Component
@ConfigurationProperties(ignoreInvalidFields = true)
public class ConfigVo {

		//Spring will populate these fields through Dependency Injection.
		@Value("${tsg.dbtype}")
		private String jdbc_dbtype;

		@Value("${tsg.connectionString}")
		private String jdbc_url;
		@Value("${tsg.username}")
		private String jdbc_username;
		@Value("${tsg.password}")
		private String jdbc_password;
		@Value("${sysadmin.email}")	
		private String sysadmin_email;
		@Value("${webapp.url}")
		private String webappurl;
		@Value("${download.url}")
		private String download_url;

		private String download_rootpath;

		private String download_cachepath;

		private String tsg_scriptpath;
		@Value("${tsg.exepath}")
		private String tsg_exepath;
		@Value("${smtp.enabled}")
		private Boolean sendEmails;
		@Value("${msgTimetoLiveDays}")
		private int msgTimetoLiveDays;
		@Value("${enableAutoCacheBuilder:false}")
		private Boolean autoCacheBuilder;
		
		@Value("${enableAggressiveCacheClearing:false}")
		private Boolean aggressiveCacheClearing;

		@Value("${azureStorageConnectionString:}")
		private String azureBlobStoreConnectionString;

		@Value("${azureStorageEndPoint:}")
		private String azureStorageEndPoint;

		@Value("${azureContainerName:nvcldatasets}")
		private String azureContainerName;

		@Value("${downloadFileMirror:}")
		private String downloadFileMirror;

		@Value("${minimumDiskspaceinGb:10}")
		private int MinDiskspace;
		
		private List<String> autoCacheFailedDatasetsList= new ArrayList<String>();

		private static final Logger logger = LogManager.getLogger(TSGDownloadRequestSvc.class);
		
		public String getJdbcDbType() {
		  return jdbc_dbtype;
		}
		 
		public List<String> getAutoCacheFailedDatasetsList() {
			return autoCacheFailedDatasetsList;
		}

		public void addItemtoAutoCacheFailedDatasetsList(String failedDataset) {
			this.autoCacheFailedDatasetsList.add(failedDataset);
		}

		public void setJdbcDbType(String jdbcDbType) {
			this.jdbc_dbtype = jdbcDbType;
		}

		public String getJdbcURL() {
		  return jdbc_url;
		}
	 
		public void setJdbcURL(String jdbcURL) {
			this.jdbc_url = jdbcURL;
		}

		public String getJdbcUsername() {
			  return jdbc_username;
		}
		 
		public void setJdbcUsername(String jdbc_username) {
			this.jdbc_username = jdbc_username;
		}

		public String getJdbcPassword() {
		  return jdbc_password;
		}
			 
		public void setJdbcPassword(String jdbc_password) {
			this.jdbc_password = jdbc_password;
		}
			
		public String getSysAdminEmail() {
			return sysadmin_email;
		}
	 
		public void setSysAdminEmail(String sysadmin_email) {
			this.sysadmin_email = sysadmin_email;
		}
	 
		public String getWebappURL() {
			return webappurl;
		}
	 
		public void setWebappURL(String webappurl) {
			this.webappurl = webappurl;
		}
		
		public String getDownloadURL() {
			return download_url;
		}
	 
		public void setDownloadURL(String download_url) {
			this.download_url = download_url;
		}
		
		public String getDownloadRootPath() {
			return download_rootpath;
		}
		

		@Value("${download.rootpath}")
		public void setDownloadRootPath(String download_rootpath) throws Exception {
			this.download_rootpath = download_rootpath;
			File dir = new File(this.download_rootpath);
			if (!dir.exists()) {
				if (dir.mkdirs()!=true) throw new Exception("download.rootpath property not set correctly or could not create folder");
			}
		}		
		
		public String getDownloadCachePath() {
			return download_cachepath;
		}


		@Value("${download.cachepath}")
		public void setDownloadCachePath(String download_cachepath) throws Exception {
			this.download_cachepath = download_cachepath;
			File dir = new File(this.download_cachepath);
			if (!dir.exists()) {
				if (dir.mkdirs()!=true) throw new Exception("download.cachepath property not set correctly or could not create folder");
			}
		}
		
		public String getTsgScriptPath() {
			return tsg_scriptpath;
		}


		@Value("${tsg.scriptpath}")
		public void setTsgScriptPath(String tsg_scriptpath) throws Exception {
			this.tsg_scriptpath = tsg_scriptpath;
			File dir = new File(this.tsg_scriptpath);
			if (!dir.exists()) {
				if (dir.mkdirs()!=true) throw new Exception("tsg.scriptpath property not set correctly or could not create folder");
			}
		}
		
		public String getTsgExePath() {
			return tsg_exepath;
		}
	 
		public void setTsgExePath(String tsg_exepath) {
			this.tsg_exepath = tsg_exepath;
		}
		
		public Boolean getSendEmails() {
			return sendEmails;
		}
	 
		public void setSendEmails(Boolean sendEmails) {
			this.sendEmails = sendEmails;
		}

		
		public void displayConfig() {
			System.out.println("jdbc.dbtype=" + this.jdbc_dbtype);
			System.out.println("jdbc.url=" + this.jdbc_url);
			System.out.println("jdbc.username="+ this.jdbc_username);
			System.out.println("jdbc.password=" + this.jdbc_password);
			System.out.println("sysadmin.email=" + this.sysadmin_email);
			//System.out.println("webapp.url=" + this.webapp_url);
			System.out.println("download.url="+ this.download_url);
			System.out.println("download.rootpath=" + this.download_rootpath);
			System.out.println("download.cachepath=" + this.download_cachepath);
			//System.out.println("download.cache=" + this.download_cache);
			System.out.println("tsg.scriptpath=" + this.tsg_scriptpath);
			System.out.println("tsg.exepath=" + this.tsg_exepath);
		}

		public int getMsgTimetoLiveDays() {
			return msgTimetoLiveDays;
		}

		public void setMsgTimetoLiveDays(int msgTimetoLiveDays) {
			this.msgTimetoLiveDays = msgTimetoLiveDays;
		}

		public Boolean getAutoCacheBuilder() {
			return autoCacheBuilder;
		}

		public void setAutoCacheBuilder(Boolean autoCacheBuilder) {
			this.autoCacheBuilder = autoCacheBuilder;
		}

		public Boolean getAggressiveCacheClearing() {
			return aggressiveCacheClearing;
		}

		public void setAggressiveCacheClearing(Boolean aggressiveCacheClearing) {
			this.aggressiveCacheClearing = aggressiveCacheClearing;
		}

		public String getAzureBlobStoreConnectionString() {
			return azureBlobStoreConnectionString;
		}

		public void setAzureBlobStoreConnectionString(String azureBlobStoreConnectionString) {
			this.azureBlobStoreConnectionString = azureBlobStoreConnectionString;
		}

		public String getAzureStorageEndPoint() {
			return azureStorageEndPoint;
		}

		public void setAzureStorageEndPoint(String azureStorageEndPoint) {
			this.azureStorageEndPoint = azureStorageEndPoint;
		}
		
		public String getAzureContainerName() {
			return azureContainerName;
		}

		public void setAzureContainerName(String azureContainerName) {
			this.azureContainerName = Utility.stringIsBlankorNull(azureContainerName)?"nvcldatasets":azureContainerName;
		}

		public String getDownloadFileMirror() {
			return downloadFileMirror;
		}

		public void setDownloadFileMirror(String downloadFileMirror) {
			this.downloadFileMirror = downloadFileMirror;
			if (this.downloadFileMirror.charAt(this.downloadFileMirror.length()-1)!='/'){
				this.downloadFileMirror= this.downloadFileMirror+'/';
			}
			try {
				URL url = new URL(this.downloadFileMirror);
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				http.setRequestMethod("HEAD");
				if (http.getResponseCode()==200 ) {
					logger.info("mirror url looks good.  Download requests that can be redirected to the mirror will be.");
				}
				else {
					logger.warn("mirror url appears to be inaccessible. falling back to no mirror connection");
					this.downloadFileMirror="";
				}
			}
			catch(Exception e) {
				logger.warn("mirror url appears to be inaccessible. falling back to no mirror connection");
				this.downloadFileMirror="";
			}
		}

		public int getMinDiskspace() {
			return MinDiskspace;
		}

		public void setMinDiskspace(int minDiskspace) {
			MinDiskspace = minDiskspace;
		}

}
