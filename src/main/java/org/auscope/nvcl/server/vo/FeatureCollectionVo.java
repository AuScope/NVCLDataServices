package org.auscope.nvcl.server.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.opengis.net/wfs",name = "FeatureCollection")
public class FeatureCollectionVo {

	private ArrayList<BoreholeViewVo> boreholeViewCollection;
	private int features;

	public FeatureCollectionVo() {
		this.boreholeViewCollection = new ArrayList<BoreholeViewVo>();
	}

	public FeatureCollectionVo(ArrayList<BoreholeViewVo> boreholeViewCollection) {
		this.boreholeViewCollection = boreholeViewCollection;
	}

    @XmlElementWrapper(namespace = "http://www.opengis.net/gml",name = "featureMembers")
	@XmlElement(namespace = "http://xmlns.geosciml.org/geosciml-portrayal/4.0",name = "BoreholeView")
	public void setBoreholeViewCollection(ArrayList<BoreholeViewVo> boreholeViewCollection)  {
		this.boreholeViewCollection=boreholeViewCollection;
	}

   	public ArrayList<BoreholeViewVo> getBoreholeViewCollection() {
   		return boreholeViewCollection;
   	}

	public int getFeatures() {
		return features;
	}

	@XmlAttribute(name="numberOfFeatures")
	public void setFeatures(int features) {
		this.features = features;
	}


}