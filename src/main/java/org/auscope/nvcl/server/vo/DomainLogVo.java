package org.auscope.nvcl.server.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Log")
@XmlType(propOrder={"logID", "logName","subdomainoflogid", "sampleCount" ,"domaindata"})
public class DomainLogVo {

    private String logID;
    private String logName;
    private String subdomainoflogid;
    private int sampleCount;

    private ArrayList<DomainDataVo> domaindata;

	public DomainLogVo() {
	}

    public DomainLogVo(String id, String name, int sn){
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

    public String getSubdomainoflogid() {
        return this.subdomainoflogid;
    }

    @XmlElement(name="Subdomainoflogid")
    public void setSubdomainoflogid(String Subdomainoflogid) {
        this.subdomainoflogid = Subdomainoflogid;
    }

    public int getSampleCount() {
        return this.sampleCount;
    }

    @XmlElement(name="SampleCount")
    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

    public void setdomaindata(ArrayList<DomainDataVo> domaindata) {
		this.domaindata = domaindata;
	}

	@XmlElement(name = "DomainData")
	public ArrayList<DomainDataVo> getdomaindata() {
		return domaindata;
	}

}
