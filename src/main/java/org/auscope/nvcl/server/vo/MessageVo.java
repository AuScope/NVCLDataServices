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
	private String requestType="";		//request type selector WFS or TSG
	// TSG download specific properties:
	private String scriptFileNameNoExt="";
	private String tSGDatasetID="";
	private boolean requestLS; 
	// WFS download specific properties:
	private String boreholeid="";
	private String featureTypeName="";

	public String getJMSTimestamp() {
		return JMSTimestamp;
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
	
	public Boolean getRequestLS(){
		return this.requestLS;
	}

	public void setRequestLS(Boolean requestLS)
	{
		this.requestLS = requestLS;
	}

}
