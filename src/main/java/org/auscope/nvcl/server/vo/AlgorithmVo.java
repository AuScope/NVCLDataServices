package org.auscope.nvcl.server.vo;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement(name = "Algorithms")
@XmlType(propOrder={"algorithmID","name","outputs"})
public class AlgorithmVo {
	private String name;
	private int algorithmID;
	private ArrayList<AlgorithmOutputVo> outputs;
	
	public AlgorithmVo() {
		this.outputs = new ArrayList<AlgorithmOutputVo>();
	}

	public int getAlgorithmID() {
		return algorithmID;
	}

	public void setAlgorithmID(int algorithmID) {
		this.algorithmID = algorithmID;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<AlgorithmOutputVo> getOutputs() {
		return outputs;
	}
	@XmlElement(name = "outputs")
	public void setOutputs(ArrayList<AlgorithmOutputVo> outputs) {
		this.outputs = outputs;
	}
	
}
