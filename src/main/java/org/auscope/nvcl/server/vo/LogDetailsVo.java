package org.auscope.nvcl.server.vo;

public class LogDetailsVo {

	private String datasetID;
    private String logId;
    private String logName;
    private int logType;
    private String domainlogId;

    public String getLogId() {
        return this.logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getLogName() {
        return this.logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public int getLogType() {
        return this.logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }
    
    public String getDomainlogId() {
        return this.domainlogId;
    }

    public void setDomainlogId(String domainlogId) {
        this.domainlogId = domainlogId;
    }

	public String getDatasetID() {
		return datasetID;
	}

	public void setDatasetID(String datasetID) {
		this.datasetID = datasetID;
	}

}
