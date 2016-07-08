package org.auscope.nvcl.server.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ImageTray")
@XmlType(propOrder={"sampleNo", "startValue", "endValue","sampleName"})
public class DomainDataVo {

    private int sampleNo;
    private float startValue;
    private float endValue;
    private String sampleName;

    @XmlElement(name="SampleNo")
    public int getSampleNo() {
        return this.sampleNo;
    }

    public void setSampleNo(int sampleNo) {
        this.sampleNo = sampleNo;
    }

    @XmlElement(name="StartValue")
    public float getStartValue() {
        return this.startValue;
    }

    public void setStartValue(float startValue) {
        this.startValue = startValue;
    }

    @XmlElement(name="EndValue")
    public float getEndValue() {
        return this.endValue;
    }

    public void setEndValue(float endValue) {
        this.endValue = endValue;
    }

    public String getSampleName() {
        return this.sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

}
