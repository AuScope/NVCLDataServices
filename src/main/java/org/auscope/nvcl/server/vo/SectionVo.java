package org.auscope.nvcl.server.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Section")
public class SectionVo {
	private int startsampleno;
	private int endsampleno;
	private int sectionnumber;
	
	public int getStartsampleno() {
		return startsampleno;
	}
	public void setStartsampleno(int startsampleno) {
		this.startsampleno = startsampleno;
	}
	public int getEndsampleno() {
		return endsampleno;
	}
	public void setEndsampleno(int endsampleno) {
		this.endsampleno = endsampleno;
	}
	public int getSectionnumber() {
		return sectionnumber;
	}
	public void setSectionnumber(int sectionnumber) {
		this.sectionnumber = sectionnumber;
	}
	
}
