package org.auscope.nvcl.server.vo;

public class ClassDataVo {

	private int samplenumber;
    private String classText;
    private int colour;
    private float depth;

    public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public int getSamplenumber() {
		return samplenumber;
	}

	public void setSamplenumber(int samplenumber) {
		this.samplenumber = samplenumber;
	}

    public String getClassText() {
        return this.classText;
    }

    public void setClassText(String classText) {
        this.classText = classText;
    }

    public int getColour() {
        return this.colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

}
