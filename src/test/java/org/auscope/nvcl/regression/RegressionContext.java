package org.auscope.nvcl.regression;


public class RegressionContext {

    private String datasetId;

    private String trayLogId;

    private String classLogId;

    private String decimalLogId;

    private String logIdSet;

    private String spectralLogId;

    public String getSpectralLogId() {
        return spectralLogId;
    }

    public void setSpectralLogId(String spectralLogId) {
        this.spectralLogId = spectralLogId;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public String getTrayLogId() {
        return trayLogId;
    }

    public void setTrayLogId(String trayLogId) {
        this.trayLogId = trayLogId;
    }

    public String getClassLogId() {
        return classLogId;
    }

    public void setClassLogId(String classLogId) {
        this.classLogId = classLogId;
    }

    public String getDecimalLogId() {
        return decimalLogId;
    }

    public void setDecimalLogId(String decimalLogId) {
        this.decimalLogId = decimalLogId;
    }

    public String getLogIdSet() {
        return logIdSet;
    }

    public void setLogIdSet(String logIdSet) {
        this.logIdSet = logIdSet;
    }
}
