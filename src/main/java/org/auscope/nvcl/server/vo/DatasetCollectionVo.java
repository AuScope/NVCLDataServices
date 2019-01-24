package org.auscope.nvcl.server.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DatasetCollection")
public class DatasetCollectionVo {

	private ArrayList<DatasetVo> DatasetCollection = new ArrayList<DatasetVo>();

	public DatasetCollectionVo() {
	}

	public DatasetCollectionVo(ArrayList<DatasetVo> datasetCollection) {
		DatasetCollection = datasetCollection;
	}

	@XmlElement(name = "Dataset")
	public ArrayList<DatasetVo> getDatasetCollection() {
		return DatasetCollection;
	}

}
