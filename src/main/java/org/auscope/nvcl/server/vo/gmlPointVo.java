package org.auscope.nvcl.server.vo;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement( namespace = "http://www.opengis.net/gml",name = "Point")
public class gmlPointVo {

    String pos;
    String lat="";
    String lon="";

    public String getPos() {
		return pos;
	}

	@XmlElement(namespace = "http://www.opengis.net/gml",name="pos")
	public void setPos(String pos) {
        this.pos = pos;
        if (this.pos.length()>0 && this.pos.contains(" ")){
            try {
                this.lat = this.pos.split(" ")[0];
                this.lon = this.pos.split(" ")[1];
            }
            catch (NumberFormatException ex){}
        }
    }

    public String getLat(){
        return lat;
    }

    public String getLon(){
        return lon;
    }
}