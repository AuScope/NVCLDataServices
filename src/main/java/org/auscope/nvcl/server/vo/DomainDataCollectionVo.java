package org.auscope.nvcl.server.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ImageTrayCollection")
public class DomainDataCollectionVo {

	private List<DomainDataVo> DomainDataCollection;

	protected DomainDataCollectionVo() {
	}

	public DomainDataCollectionVo(List<DomainDataVo> domainDataCollection) {
		this.setDomainDataCollection(domainDataCollection);
	}

	@XmlElement(name = "ImageTray")
	public List<DomainDataVo> getDomainDataCollection() {
		return DomainDataCollection;
	}

	public void setDomainDataCollection(List<DomainDataVo> domainDataCollection) {
		DomainDataCollection = domainDataCollection;
	}

}
