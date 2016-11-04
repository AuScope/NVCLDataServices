package org.auscope.nvcl.server.vo;

/**
 * This Config Value Object class set the configuration info from config.properties 
 * and allow getting the values thru getter and setter method.
 * 
 * @author Florence Tan
 */

public class ConfigVo {

		//Spring will populate these fields through Dependency Injection.
	    private String jdbc_dbtype;
		private String jdbc_url;
		private String jdbc_username;
		private String jdbc_password;		
		private String sysadmin_email;
		private String webappurl;
		private String download_url;
		private String download_rootpath;
		private String download_cachepath;
		private String geoserverUrl;
		private String tsg_scriptpath;
		private String tsg_exepath;
		private Boolean sendEmails;
		private int msgTimetoLiveDays;
		//extra field to be use for creating request queue message
		//private String requestorEmail;
		//private String requestType;
		//private String serviceUrl;
		//private String typeName;
		//private String boreholeid;
		//private String tSGdatasetid;
		//private String scriptFileNameNoExt;
		//private Boolean requestLS;
		
		public String getJdbcDbType() {
		  return jdbc_dbtype;
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
	 
		public void setDownloadRootPath(String download_rootpath) {
			this.download_rootpath = download_rootpath;
		}		
		
		public String getDownloadCachePath() {
			return download_cachepath;
		}
	 
		public void setDownloadCachePath(String download_cachepath) {
			this.download_cachepath = download_cachepath;
		}
		
		public String getGeoserverUrl() {
			return geoserverUrl;
		}
	 
		public void setGeoserverUrl(String geoserverUrl) {
			this.geoserverUrl = geoserverUrl;
		}
		
		public String getTsgScriptPath() {
			return tsg_scriptpath;
		}
	 
		public void setTsgScriptPath(String tsg_scriptpath) {
			this.tsg_scriptpath = tsg_scriptpath;
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

		/*public String getRequestorEmail() {
			return requestorEmail;
		}
	 
		public void setRequestorEmail(String requestorEmail) {
			this.requestorEmail = requestorEmail;
		}
		
		public String getRequestType() {
			return requestType;
		}
	 
		public void setRequestType(String requestType) {
			this.requestType = requestType;
		}
		
		public String getTypeName() {
			return typeName;
		}
	 
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
		
		public String getBoreholeID() {
			return boreholeid;
		}
	 
		public void setBoreholeID(String boreholeid) {
			this.boreholeid = boreholeid;
		}

		public String getTSGdatasetid() {
			return tSGdatasetid;
		}
	 
		public void setTSGdatasetid(String tSGdatasetid) {
			this.tSGdatasetid = tSGdatasetid;
		}
		
		public String getScriptFileNameNoExt() {
			return scriptFileNameNoExt;
		}
	 
		public void setScriptFileNameNoExt(String scriptFileNameNoExt) {
			this.scriptFileNameNoExt = scriptFileNameNoExt;
		}*/
		
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
		/*public Boolean getRequestLS(){
			return this.requestLS;
		}
		
		public void setRequestLS(Boolean requestLS)
		{
			this.requestLS = requestLS;
		}*/

}
