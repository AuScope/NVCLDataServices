package org.auscope.nvcl.server.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "LogCollection")
public class DomainLogCollectionVo {

	private ArrayList<DomainLogVo> domainLogCollection;

	protected DomainLogCollectionVo() {
		domainLogCollection = new ArrayList<DomainLogVo>();
	}

	public DomainLogCollectionVo(ArrayList<DomainLogVo> domainLogCollection) {
		this.domainLogCollection = domainLogCollection;
	}

	@XmlElement(name = "Log")
	public ArrayList<DomainLogVo> getdomainLogCollection() {
		return domainLogCollection;
	}
}
