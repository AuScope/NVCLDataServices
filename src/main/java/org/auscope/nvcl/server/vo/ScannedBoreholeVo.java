package org.auscope.nvcl.server.vo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(namespace = "http://www.auscope.org/nvcl",name = "ScannedBoreholeCollection")
public class ScannedBoreholeVo implements Comparable<ScannedBoreholeVo> {
	String id;
	BoreholeVo boreholevo;
	String lat;
	String lon;
	String name;
	String dSID;

	public String getdSID() {
		return dSID;
	}

	public void setdSID(String dSID) {
		this.dSID = dSID;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	@XmlAttribute(namespace = "http://www.opengis.net/gml",name="id")
	public void setId(String id) {
		this.id = id;
	}

	public BoreholeVo getBoreholevo() {
		return boreholevo;
	}

	@XmlElement(namespace = "http://www.auscope.org/nvcl",name = "scannedBorehole")
	public void setBoreholevo(BoreholeVo boreholevo) {
		this.boreholevo = boreholevo;
	}

    @Override
    public int compareTo(ScannedBoreholeVo comparetitle) {
        return this.boreholevo.getTitle().compareTo(comparetitle.boreholevo.getTitle());
    }


}