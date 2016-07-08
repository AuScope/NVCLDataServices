package org.auscope.nvcl.server.vo;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "ProfLog")
@XmlType(propOrder={"logID", "logName","sampleCount","floatsPerSample","minVal","maxVal"})
public class ProfLogVo {
	

	private String logID;
	private String logName;
	private int sampleCount;
	private int floatsPerSample;
	private float minVal;
	private float maxVal;


	public ProfLogVo() {
	}

	public String getLogID() {
		return logID;
	}

	public void setLogID(String logID) {
		this.logID = logID;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public int getSampleCount() {
		return sampleCount;
	}

	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}

	public int getFloatsPerSample() {
		return floatsPerSample;
	}

	public void setFloatsPerSample(int floatsPerSample) {
		this.floatsPerSample = floatsPerSample;
	}

	public float getMinVal() {
		return minVal;
	}

	public void setMinVal(float minVal) {
		this.minVal = minVal;
	}

	public float getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(float maxVal) {
		this.maxVal = maxVal;
	}

	
}
