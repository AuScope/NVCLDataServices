package org.auscope.nvcl.server.vo;

public class BinnedClassDataVo {

    private float roundedDepth;
    private int classCount;
    private String classText;
    private int colour;

    public float getRoundedDepth() {
        return this.roundedDepth;
    }

    public void setRoundedDepth(float roundedDepth) {
        this.roundedDepth = roundedDepth;
    }

    public int getClassCount() {
        return this.classCount;
    }

    public void setClassCount(int classCount) {
        this.classCount = classCount;
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
