package org.auscope.nvcl.server.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ImageTrayCollection")
public class DomainDataCollectionVo {

	private ArrayList<DomainDataVo> DomainDataCollection;

	protected DomainDataCollectionVo() {
	}

	public DomainDataCollectionVo(ArrayList<DomainDataVo> domainDataCollection) {
		this.setDomainDataCollection(domainDataCollection);
	}

	@XmlElement(name = "ImageTray")
	public ArrayList<DomainDataVo> getDomainDataCollection() {
		return DomainDataCollection;
	}

	public void setDomainDataCollection(ArrayList<DomainDataVo> domainDataCollection) {
		DomainDataCollection = domainDataCollection;
	}

}
