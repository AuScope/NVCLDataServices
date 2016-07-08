package org.auscope.nvcl.server.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SpectralLogCollection")
public class SpectralLogCollectionVo {
	
	private ArrayList<SpectralLogVo> SpectralLogCollection;

	protected SpectralLogCollectionVo() {
	}

	public SpectralLogCollectionVo(ArrayList<SpectralLogVo> spectralLogCollection) {
		this.SpectralLogCollection = spectralLogCollection;
	}

	@XmlElement(name = "SpectralLog")
	public ArrayList<SpectralLogVo> getSpectralLogCollection() {
		return SpectralLogCollection;
	}
}
