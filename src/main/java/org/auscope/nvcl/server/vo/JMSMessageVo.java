package org.auscope.nvcl.server.vo;

/**
 * This message value object class set the necessary config and url parameter info from
 * ConfigVo and TSGParamVo objects into MessageVo object 
 * 
 * @author Florence Tan
 */

public class JMSMessageVo {


		private String JMSTimestamp;
		private String JMSMsgID;
		private String JMSCorrelationID;
		private String status;
		private String description;
		private Boolean resultfromcache;
		private String tsgdatasetid;
		private boolean requestLS; 
		private String boreholeid;
		private String typename;
		
		public String getJMSTimestamp() {
			return JMSTimestamp;
		}
	 
		public void setJMSTimestamp(String JMSTimestamp) {
			this.JMSTimestamp = JMSTimestamp;
		}
		
		public String getJMSMsgID() {
			return JMSMsgID;
		}
	 
		public void setJMSMsgID(String JMSMsgID) {
			this.JMSMsgID = JMSMsgID;
		}
		
		public String getJMSCorrelationID() {
			return JMSCorrelationID;
		}
	 
		public void setJMSCorrelationID(String JMSCorrelationID) {
			this.JMSCorrelationID = JMSCorrelationID;
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
		
		public Boolean getResultfromcache() {
			return resultfromcache;
		}
	 
		public void setResultfromcache(Boolean resultfromcache) {
			this.resultfromcache = resultfromcache;
		}
		
		public Boolean getRequestLS(){
			return this.requestLS;
		}
		
		public void setRequestLS(Boolean requestLS)
		{
			this.requestLS = requestLS;
		}
		
		public String getTsgdatasetid() {
			return tsgdatasetid;
		}
	 
		public void setTsgdatasetid(String tSGdatasetid) {
			this.tsgdatasetid = tSGdatasetid;
		}
		public String getBoreholeid() {
			return boreholeid;
		}
	 
		public void setBoreholeid(String boreholeid) {
			this.boreholeid = boreholeid;
		}
		public String getTypename() {
			return typename;
		}
	 
		public void setTypename(String typename) {
			this.typename = typename;
		}
	 }
