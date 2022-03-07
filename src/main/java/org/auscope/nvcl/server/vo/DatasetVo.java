package org.auscope.nvcl.server.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Dataset")
@XmlType(propOrder={"datasetID","boreholeURI", "datasetName","description","trayID","sectionID","domainID","depthRange","spectralLogCollection","imageLogCollection","logCollection","profLogCollection"})
public class DatasetVo {

    private String datasetID;
    private String boreholeURI;
	private String datasetName;
	private String description;
	private SpectralLogCollectionVo spectralLogCollection;
	private ImageLogCollectionVo imageLogCollection;
	private ProfLogCollectionVo profLogCollection;
	private LogCollectionVo logCollection;
	private DepthRangeVo depthRange;
	private String trayID;
	private String sectionID;
	private String domainID;
	
	public String getBoreholeURI() {
		return boreholeURI;
	}
	public void setBoreholeURI(String boreholeID) {
		this.boreholeURI = boreholeID;
	}

	public String getDomainID() {
		return domainID;
	}
	public void setDomainID(String domainID) {
		this.domainID = domainID;
	}
	public String getTrayID() {
		return trayID;
	}
	public void setTrayID(String trayID) {
		this.trayID = trayID;
	}
	public String getSectionID() {
		return sectionID;
	}
	public void setSectionID(String sectionID) {
		this.sectionID = sectionID;
	}

	public String getDatasetID() {
        return this.datasetID;
    }
	@XmlElement(name = "DatasetID")
    public void setDatasetID(String datasetID) {
        this.datasetID = datasetID;
    }

    public String getDatasetName() {
        return this.datasetName;
    }
    @XmlElement(name = "DatasetName")
    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }
    
	public SpectralLogCollectionVo getSpectralLogCollection() {
		return spectralLogCollection;
	}
	
	@XmlElement(name = "SpectralLogs")
	public void setSpectralLogCollection(SpectralLogCollectionVo spectralLogCollection) {
		this.spectralLogCollection = spectralLogCollection;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ImageLogCollectionVo getImageLogCollection() {
		return imageLogCollection;
	}
	@XmlElement(name = "ImageLogs")
	public void setImageLogCollection(ImageLogCollectionVo imageLogCollection) {
		this.imageLogCollection = imageLogCollection;
	}
	public LogCollectionVo getLogCollection() {
		return logCollection;
	}
	@XmlElement(name = "Logs")
	public void setLogCollection(LogCollectionVo logCollection) {
		this.logCollection = logCollection;
	}
	public ProfLogCollectionVo getProfLogCollection() {
		return profLogCollection;
	}
	@XmlElement(name = "ProfilometerLogs")
	public void setProfLogCollection(ProfLogCollectionVo profLogCollection) {
		this.profLogCollection = profLogCollection;
	}
	@XmlElement(name = "DepthRange")
	public DepthRangeVo getDepthRange() {
		return depthRange;
	}
	public void setDepthRange(DepthRangeVo depthRange) {
		this.depthRange = depthRange;
	}
}
