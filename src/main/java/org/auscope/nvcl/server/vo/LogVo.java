package org.auscope.nvcl.server.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Log")
@XmlType(propOrder={"logID", "logName","ispublic","logType","algorithmoutID","maskLogID"})
public class LogVo {

	private String logID;
	private String logName;
	private boolean ispublic;
	private int algorithmoutID;
	public int getAlgorithmoutID() {
		return algorithmoutID;
	}

	public void setAlgorithmoutID(int algorithmoutID) {
		this.algorithmoutID = algorithmoutID;
	}

	private String maskLogID;
	private int logType;

	public String getMaskLogID() {
		return maskLogID;
	}

	public void setMaskLogID(String maskLogID) {
		this.maskLogID = maskLogID;
	}

	public int getLogType() {
		return logType;
	}

	public void setLogType(int logType) {
		this.logType = logType;
	}

	public LogVo(){
	}

	public String getLogID() {
		return this.logID;
	}

	@XmlElement(name="LogID")
	public void setLogID(String logID) {
		this.logID = logID;
	}


	public String getLogName() {
		return this.logName;
	}

	@XmlElement(name="logName")
	public void setLogName(String logName) {
		this.logName = logName;
	}

	public boolean isIspublic() {
		return ispublic;
	}

	public void setIspublic(boolean ispublic) {
		this.ispublic = ispublic;
	}
}