package org.auscope.nvcl.server.vo;

import java.net.URI;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "DownloadUrl")
@XmlType(propOrder={"datasetName","datasetID","url"})
public class downloadUrl {

    private URI Url;
    private String datasetName;
    public String getDatasetName() {
        return datasetName;
    }


    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }


    public String getDatasetID() {
        return datasetID;
    }


    public void setDatasetID(String datasetID) {
        this.datasetID = datasetID;
    }


    private String datasetID;

    

    public URI getUrl() {
        return Url;
    }


    public void setUrl(URI Url) {
        this.Url = Url;
    }


    public downloadUrl() {
    }



}

