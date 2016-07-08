package org.auscope.nvcl.server.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.opengis.net/wfs",name = "FeatureCollection")
public class FeatureCollectionVo {

	private ArrayList<ScannedBoreholeVo> scannedboreholeCollection;
	private int features;

	public FeatureCollectionVo() {
		this.scannedboreholeCollection = new ArrayList<ScannedBoreholeVo>();
	}

	public FeatureCollectionVo(ArrayList<ScannedBoreholeVo> scannedboreholeCollection) {
		this.scannedboreholeCollection = scannedboreholeCollection;
	}

    @XmlElementWrapper(namespace = "http://www.opengis.net/gml",name = "featureMembers")
	@XmlElement(namespace = "http://www.auscope.org/nvcl",name = "ScannedBoreholeCollection")
	public void setscannedboreholeCollection(ArrayList<ScannedBoreholeVo> scannedboreholeCollection)  {
		this.scannedboreholeCollection=scannedboreholeCollection;
	}

   	public ArrayList<ScannedBoreholeVo> getscannedboreholeCollection() {
   		return scannedboreholeCollection;
   	}

	public int getFeatures() {
		return features;
	}

	@XmlAttribute(name="numberOfFeatures")
	public void setFeatures(int features) {
		this.features = features;
	}


}