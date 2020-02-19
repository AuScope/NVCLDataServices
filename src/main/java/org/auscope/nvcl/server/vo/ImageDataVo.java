package org.auscope.nvcl.server.vo;


public class ImageDataVo {

    private int sampleNo;
    private byte[] imgData;
    private byte[] imgHistogramLUT;

    public int getSampleNo() {
        return this.sampleNo;
    }

    public void setSampleNo(int sampleNo) {
        this.sampleNo = sampleNo;
    }

    public byte[] getImgData() {
        return this.imgData;
    }

    public void setImgData(byte[] imgData) {
        this.imgData = imgData;
    }

    public byte[] getImgHistogramLUT() {
        return imgHistogramLUT;
    }

    public void setImgHistogramLUT(byte[] imgHistogramLUT) {
        this.imgHistogramLUT = imgHistogramLUT;
    }

}
