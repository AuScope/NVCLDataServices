package org.auscope.nvcl.server.vo;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "depthRange")
@XmlType(propOrder={"start", "end"})
public class DepthRangeVo {

    private float start;
	private float end;
    
    public DepthRangeVo() {
    }

    public float getstart() {
        return start;
    }

    public void setstart(float start) {
        this.start = start;
    }

    public float getend() {
        return end;
    }

    public void setend(float end) {
        this.end = end;
    }

}

