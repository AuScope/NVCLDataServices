package org.auscope.nvcl.server.vo;


public class LogExtentsVo {

	public LogExtentsVo(float minvalue, float maxvalue) {
		super();
		this.minvalue = minvalue;
		this.maxvalue = maxvalue;
	}
	private float minvalue;
	private float maxvalue;
	
	public float getMinvalue() {
		return minvalue;
	}
	public void setMinvalue(float minvalue) {
		this.minvalue = minvalue;
	}
	public float getMaxvalue() {
		return maxvalue;
	}
	public void setMaxvalue(float maxvalue) {
		this.maxvalue = maxvalue;
	}


}