package org.auscope.nvcl.server.vo;



import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(namespace = "http://xmlns.geosciml.org/geosciml-portrayal/4.0",name = "BoreholeView")
public class BoreholeViewVo implements Comparable<BoreholeViewVo> {
	String gmlId = new String();
	String identifier;
	ArrayList<gmlPointVo> shape;
	String name;
	DatasetCollectionVo datasets;

	public BoreholeViewVo() {
		datasets = new DatasetCollectionVo();
	}

	public DatasetCollectionVo getDatasets() {
		return datasets;
	}

	public void setDatasets(DatasetCollectionVo datasets) {
		this.datasets = datasets;
	}

	@XmlElement(namespace = "http://xmlns.geosciml.org/geosciml-portrayal/4.0",name="identifier")
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}


	public String getName() {
		return name;
	}

	@XmlElement(namespace = "http://xmlns.geosciml.org/geosciml-portrayal/4.0",name="name")
	public void setName(String name) {
		this.name = name;
	}

	public String getGmlId() {
		return gmlId;
	}

	@XmlAttribute(namespace = "http://www.opengis.net/gml",name="id")
	public void setGmlId(String gmlId) {
		this.gmlId = gmlId;
	}

	public ArrayList<gmlPointVo> getShape() {
		return shape;
	}

	@XmlElementWrapper(namespace = "http://xmlns.geosciml.org/geosciml-portrayal/4.0",name = "shape")
	@XmlElement(namespace = "http://www.opengis.net/gml",name="Point")
	public void setShape(ArrayList<gmlPointVo> shape) {
		this.shape = shape;
	}

    @Override
	public int compareTo(BoreholeViewVo comparegmlid) {
		try {
        return this.getGmlId().compareTo(comparegmlid.getGmlId());
		}
		catch(NullPointerException ex ){
			return 1;
		}
	}


}