package org.auscope.nvcl.server.vo;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "AlgorithmOutputVersions")
@XmlType(propOrder={"algorithmoutputID","version"})
public class AlgorithmOutputVersionVo {
	private int algorithmoutputID;
	private int version;
	
	public AlgorithmOutputVersionVo() {
	}

	AlgorithmOutputVersionVo(int algoutputid,int ver){
		algorithmoutputID = algoutputid;
		version=ver;
	}
	
	public int getAlgorithmoutputID() {
		return algorithmoutputID;
	}
	public void setAlgorithmoutputID(int algorithmoutputID) {
		this.algorithmoutputID = algorithmoutputID;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	
}
