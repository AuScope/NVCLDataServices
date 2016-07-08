package org.auscope.nvcl.server.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ProfLogCollection")
public class ProfLogCollectionVo {
	
	private ArrayList<ProfLogVo> ProfLogCollection;

	protected ProfLogCollectionVo() {
	}

	public ProfLogCollectionVo(ArrayList<ProfLogVo> profLogCollection) {
		this.ProfLogCollection = profLogCollection;
	}

	@XmlElement(name = "ProfLog")
	public ArrayList<ProfLogVo> getProfLogCollection() {
		return ProfLogCollection;
	}
}
