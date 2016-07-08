package org.auscope.nvcl.server.vo;

import java.sql.Blob;

public class ImageDataVo {

    private int sampleNo;
    private Blob imgData;

    public int getSampleNo() {
        return this.sampleNo;
    }

    public void setSampleNo(int sampleNo) {
        this.sampleNo = sampleNo;
    }

    public Blob getImgData() {
        return this.imgData;
    }

    public void setImgData(Blob imgData) {
        this.imgData = imgData;
    }

}
