package org.auscope.nvcl.server.vo;

/**
 * This message value object class set the necessary config and url parameter info from
 * ConfigVo and TSGParamVo objects into MessageVo object 
 * 
 * @author Florence Tan
 */

public class MessageVo {


		private String tsg_exepath;
		private String tsg_scriptpath;
		private String scriptFileNameNoExt;
		private Boolean resultfromcache;
		private String download_cachepath;
		private String download_url;
		private String download_rootpath;
		private String requestorEmail;
		private String boreholeId;
		//private String serviceUrl;
		private String typeName;
		private String status;
		private String description;
		private String tSGDatasetID;
		private boolean requestLS; 
		
		public String getTsgExePath() {
			return tsg_exepath;
		}
	 
		public void setTsgExePath(String tsg_exepath) {
			this.tsg_exepath = tsg_exepath;
		}
		
		public String getTsgScriptPath() {
			return tsg_scriptpath;
		}
	 
		public void setTsgScriptPath(String tsg_scriptpath) {
			this.tsg_scriptpath = tsg_scriptpath;
		}
		
		public String getScriptFileNameNoExt() {
			return scriptFileNameNoExt;
		}
	 
		public void setScriptFileNameNoExt(String scriptFileNameNoExt) {
			this.scriptFileNameNoExt = scriptFileNameNoExt;
		}
	 
		public Boolean getResultfromcache() {
			return resultfromcache;
		}
	 
		public void setResultfromcache(Boolean resultfromcache) {
			this.resultfromcache = resultfromcache;
		}
		
		public String getDownloadCachePath() {
			return download_cachepath;
		}
	 
		public void setDownloadCachePath(String download_cachepath) {
			this.download_cachepath = download_cachepath;
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
				
	
		public String getRequestorEmail() {
			return requestorEmail;
		}
	 
		public void setRequestorEmail(String requestorEmail) {
			this.requestorEmail = requestorEmail;
		}

		public String getBoreholeId() {
			return boreholeId;
		}
	 
		public void setBoreholeId(String boreholeId) {
			this.boreholeId = boreholeId;
		}
		
		//public String getServiceUrl() {
		//	return serviceUrl;
		//}
	 
		//public void setServiceUrl(String serviceUrl) {
		//	this.serviceUrl = serviceUrl;
		//}


		public String getTypeName() {
			return typeName;
		}
	 
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		public String getStatus() {
			return status;
		}
		
		public void setStatus(String status) {
			this.status = status;
		}
		
		public String getDescription() {
			return description;
		}
		
		public void setDescription(String description) {
			this.description = description;
		}
		
		public String getTSGDatasetID(){
			return tSGDatasetID;
		}
		public void setTSGDatasetID(String tSGDatasetID){
			this.tSGDatasetID=tSGDatasetID;
		}
		public Boolean getRequestLS(){
			return this.requestLS;
		}
		
		public void setRequestLS(Boolean requestLS)
		{
			this.requestLS = requestLS;
		}
		
	 }
