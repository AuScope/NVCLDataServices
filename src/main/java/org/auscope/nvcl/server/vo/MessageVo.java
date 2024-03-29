package org.auscope.nvcl.server.vo;


/**
 * This message value object class set the necessary config and url parameter info from
 * ConfigVo and TSGParamVo objects into MessageVo object 
 * 
 * @author Florence Tan
 */

public class MessageVo {

	// generic properties
	private String JMSTimestamp;
	private String JMSMsgID;
	private String JMSCorrelationID;
	private String requestorEmail="";
	private String status="";
	private String description="";
	private boolean resultfromcache;
	private boolean forcerecreate=false;
	private String requestType="";		//request type selector WFS or TSG
	// TSG download specific properties:
	private String scriptFileNameNoExt="";
	private String tSGDatasetID="";

	// WFS download specific properties:
	private String boreholeid="";
	private String featureTypeName="";
	private Boolean autoCacheJob=false;
	private String datasetname="";
	private Long dbModifiedDate;

	public String getJMSTimestamp() {
		return JMSTimestamp;
	}

	public Long getDbModifiedDate() {
		return dbModifiedDate;
	}

	public void setDbModifiedDate(Long dbModifiedDate) {
		this.dbModifiedDate = dbModifiedDate;
	}

	public String getDatasetname() {
		return datasetname;
	}

	public void setDatasetname(String datasetname) {
		this.datasetname = datasetname;
	}

	public Boolean getAutoCacheJob() {
		return autoCacheJob;
	}

	public void setAutoCacheJob(Boolean autoCacheJob) {
		this.autoCacheJob = autoCacheJob;
	}

	public void setJMSTimestamp(String jMSTimestamp) {
		JMSTimestamp = jMSTimestamp;
	}

	public String getJMSMsgID() {
		return JMSMsgID;
	}

	public void setJMSMsgID(String jMSMsgID) {
		JMSMsgID = jMSMsgID;
	}

	public String getJMSCorrelationID() {
		return JMSCorrelationID;
	}

	public void setJMSCorrelationID(String jMSCorrelationID) {
		JMSCorrelationID = jMSCorrelationID;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
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

	public boolean isForcerecreate() {
		return forcerecreate;
	}

	public void setForcerecreate(boolean forcerecreate) {
		this.forcerecreate = forcerecreate;
	}

	public String getRequestorEmail() {
		return requestorEmail;
	}

	public void setRequestorEmail(String requestorEmail) {
		this.requestorEmail = requestorEmail;
	}

	public String getBoreholeid() {
		return boreholeid;
	}

	public void setBoreholeid(String boreholeId) {
		this.boreholeid = boreholeId;
	}

	public String getFeatureTypeName() {
		return featureTypeName;
	}

	public void setFeatureTypeName(String typeName) {
		this.featureTypeName = typeName;
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

	public String gettSGDatasetID() {
		return tSGDatasetID;
	}

	public void settSGDatasetID(String tSGDatasetID) {
		this.tSGDatasetID = tSGDatasetID;
	}

}
