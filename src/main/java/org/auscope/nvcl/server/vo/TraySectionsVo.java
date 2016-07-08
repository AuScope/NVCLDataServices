package org.auscope.nvcl.server.vo;


import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Tray")
public class TraySectionsVo {
	private ArrayList<SectionVo> sections;

	public TraySectionsVo(ArrayList<SectionVo> sections) {
		super();
		this.sections = sections;
	}

	public ArrayList<SectionVo> getSections() {
		return sections;
	}

	public void setSections(ArrayList<SectionVo> sections) {
		this.sections = sections;
	}

}
