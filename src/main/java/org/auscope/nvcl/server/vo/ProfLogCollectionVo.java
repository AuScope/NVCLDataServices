package org.auscope.nvcl.server.vo;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ProfLogCollection")
public class ProfLogCollectionVo {
	
	private ArrayList<ProfLogVo> ProfLogCollection;

	protected ProfLogCollectionVo() {
		ProfLogCollection = new ArrayList<ProfLogVo>();
	}

	public ProfLogCollectionVo(ArrayList<ProfLogVo> profLogCollection) {
		this.ProfLogCollection = profLogCollection;
	}

	@XmlElement(name = "ProfLog")
	public ArrayList<ProfLogVo> getProfLogCollection() {
		return ProfLogCollection;
	}
}
