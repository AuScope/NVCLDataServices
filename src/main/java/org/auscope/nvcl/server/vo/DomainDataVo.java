package org.auscope.nvcl.server.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ImageTray")
@XmlType(propOrder={"sampleNo", "startValue", "endValue","startIndex","endIndex","sampleName"})
public class DomainDataVo {

    private int sampleNo;
    private Float startValue;
    private Integer startIndex;
    private Float endValue;
    private Integer endIndex;
    private String sampleName;

    @XmlElement(name="SampleNo")
    public int getSampleNo() {
        return this.sampleNo;
    }

    public void setSampleNo(int sampleNo) {
        this.sampleNo = sampleNo;
    }

    @XmlElement(name="StartValue")
    public Float getStartValue() {
        return this.startValue;
    }

    public void setStartValue(float startValue) {
        this.startValue = startValue;
    }
    @XmlElement(name="startIndex")
    public Integer getstartIndex() {
        return startIndex;
    }

    public void setstartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    @XmlElement(name="EndValue")
    public Float getEndValue() {
        return this.endValue;
    }

    public void setEndValue(float endValue) {
        this.endValue = endValue;
    }

    @XmlElement(name="endIndex")
    public Integer getendIndex() {
        return endIndex;
    }

    public void setendIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    public String getSampleName() {
        return this.sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

}
