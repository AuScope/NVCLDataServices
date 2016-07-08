package org.auscope.nvcl.server.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "AlgorithmOutputs")
@XmlType(propOrder={"name","versions"})
public class AlgorithmOutputVo {

	private String name;
	private ArrayList<AlgorithmOutputVersionVo> versions;
	
	public AlgorithmOutputVo() {
		this.versions = new ArrayList<AlgorithmOutputVersionVo>();
	}
	
	public AlgorithmOutputVo(String newname) {
		name = newname;
		versions = new ArrayList<AlgorithmOutputVersionVo>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<AlgorithmOutputVersionVo> getVersions() {
		return versions;
	}
	@XmlElement(name = "versions")
	public void setVersions(ArrayList<AlgorithmOutputVersionVo> versions) {
		this.versions = versions;
	}
	
	public void addVersion(int algoutputid, int version){
		this.versions.add(new AlgorithmOutputVersionVo(algoutputid,version));
	}
	
}
