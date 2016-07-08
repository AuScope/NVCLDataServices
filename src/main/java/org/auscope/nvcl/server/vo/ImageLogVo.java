package org.auscope.nvcl.server.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Log")
@XmlType(propOrder={"logID", "logName", "sampleCount"})
public class ImageLogVo {

    private String logID;
    private String logName;
    private int sampleCount;

	public ImageLogVo() {
	}

    public ImageLogVo(String id, String name, int sn){
    	this.setLogID(id);
    	this.setLogName(name);
    	this.setSampleCount(sn);
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

    @XmlElement(name="LogName")
    public void setLogName(String logName) {
        this.logName = logName;
    }

    public int getSampleCount() {
        return this.sampleCount;
    }

    @XmlElement(name="SampleCount")
    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

}
