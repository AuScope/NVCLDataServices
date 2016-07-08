package org.auscope.nvcl.server.vo;

public class MaskDataVo {

	private int samplenumber;
    private float depth;
    private Boolean value=null;

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

    public Boolean getvalue() {
        return this.value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

}
