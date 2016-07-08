package org.auscope.nvcl.server.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ClassificationsCollection")
public class ClassificationsCollectionVo {
	
	private ArrayList<ClassificationVo> classifications;

	public ClassificationsCollectionVo() {
		this.classifications = new ArrayList<ClassificationVo>();
	}
	public ClassificationsCollectionVo(ArrayList<ClassificationVo> classes) {
		this.classifications = classes;
	}

	public ArrayList<ClassificationVo> getClassifications() {
		return classifications;
	}

	public void setClassifications(ArrayList<ClassificationVo> classifications) {
		this.classifications = classifications;
	}
	
}
