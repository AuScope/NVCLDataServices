package org.auscope.nvcl.server.vo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.auscope.org/nvcl",name = "scannedBorehole")
public class BoreholeVo {
	String title;
	String URI;

	public BoreholeVo(String title, String uRI) {
		super();
		this.title = title;
		URI = uRI;
	}

	public BoreholeVo() {
		super();
	}

	public String getTitle() {
		return title;
	}

	@XmlAttribute(namespace= "http://www.w3.org/1999/xlink",name="title")
	public void setTitle(String title) {
		this.title = title;
	}

	public String getURI() {
		return URI;
	}


	@XmlAttribute(namespace= "http://www.w3.org/1999/xlink",name="href")
	public void setURI(String uRI) {
		URI = uRI;
	}

}
