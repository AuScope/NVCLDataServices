package org.auscope.nvcl.server.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "LogCollection")
public class ImageLogCollectionVo {

	private ArrayList<ImageLogVo> imageLogCollection;

	protected ImageLogCollectionVo() {
	}

	public ImageLogCollectionVo(ArrayList<ImageLogVo> imageLogCollection) {
		this.imageLogCollection = imageLogCollection;
	}

	@XmlElement(name = "Log")
	public ArrayList<ImageLogVo> getimageLogCollection() {
		return imageLogCollection;
	}
}
