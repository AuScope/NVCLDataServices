package org.auscope.nvcl.server.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.opengis.net/wfs",name = "FeatureCollection")
public class BoreholeFeatureCollectionVo {

	private ArrayList<BoreholeVo> boreholeCollection;
	private int features;

	public BoreholeFeatureCollectionVo() {
		this.boreholeCollection = new ArrayList<BoreholeVo>();
	}

	public BoreholeFeatureCollectionVo(ArrayList<BoreholeVo> boreholeCollection) {
		this.boreholeCollection = boreholeCollection;
	}

    @XmlElementWrapper(namespace = "http://www.opengis.net/gml",name = "featureMembers")
	@XmlElement(namespace = "urn:cgi:xmlns:CGI:GeoSciML:2.0",name = "Borehole")
	public void setBoreholeCollection(ArrayList<BoreholeVo> boreholeCollection)  {
		this.boreholeCollection=boreholeCollection;
	}

   	public ArrayList<BoreholeVo> getBoreholeCollection() {
   		return boreholeCollection;
   	}

	public int getFeatures() {
		return features;
	}

	@XmlAttribute(name="numberOfFeatures")
	public void setFeatures(int features) {
		this.features = features;
	}


}