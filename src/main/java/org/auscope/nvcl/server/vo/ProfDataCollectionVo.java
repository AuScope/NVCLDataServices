package org.auscope.nvcl.server.vo;

import java.util.ArrayList;



public class ProfDataCollectionVo {

	private ArrayList<ProfDataVo> ProfDataCollection;

	protected ProfDataCollectionVo() {
	}

	public ProfDataCollectionVo(ArrayList<ProfDataVo> profDataCollection) {
		this.setProfDataCollection(profDataCollection);
	}

	public ArrayList<ProfDataVo> getProfDataCollection() {
		return ProfDataCollection;
	}

	public void setProfDataCollection(ArrayList<ProfDataVo> profDataCollection) {
		ProfDataCollection = profDataCollection;
	}

}
