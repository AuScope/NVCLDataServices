package org.auscope.nvcl.server.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
