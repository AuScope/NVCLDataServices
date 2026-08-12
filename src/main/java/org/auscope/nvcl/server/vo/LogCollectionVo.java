package org.auscope.nvcl.server.vo;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "LogCollection")
public class LogCollectionVo {

	private ArrayList<LogVo> LogCollection;

	protected LogCollectionVo() {
		LogCollection= new ArrayList<LogVo>();
	}

	public LogCollectionVo(ArrayList<LogVo> logCollection) {
		this.LogCollection = logCollection;
	}

	@XmlElement(name = "Log")
	public ArrayList<LogVo> getLogCollection() {
		return LogCollection;
	}

}
