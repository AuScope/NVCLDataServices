package org.auscope.nvcl.server.vo;

public class FloatDataVo {

	private int samplenumber;
    private float depth;
    private Float value=null;

    public int getSamplenumber() {
		return samplenumber;
	}

	public void setSamplenumber(int samplenumber) {
		this.samplenumber = samplenumber;
	}
	
    public float getDepth() {
        return this.depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public Float getvalue() {
        return this.value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

}
