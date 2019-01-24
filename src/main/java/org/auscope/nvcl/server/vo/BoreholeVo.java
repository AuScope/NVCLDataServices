package org.auscope.nvcl.server.vo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "urn:cgi:xmlns:CGI:GeoSciML:2.0",name = "Borehole")
public class BoreholeVo {

	String gmlid;

	public BoreholeVo(String gmlid) {
		super();
		this.gmlid = gmlid;
	}

	public BoreholeVo() {
		super();
	}

	public String getGmlid() {
		return gmlid;
	}

	@XmlAttribute(namespace= "http://www.opengis.net/gml",name="id")
	public void setGmlid(String gmlid) {
		this.gmlid = gmlid;
	}

}
