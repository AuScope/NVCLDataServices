package org.auscope.nvcl.server.vo;

/**
 * This Config Value Object class set the configuration info from
 * config.properties and allow getting the values through getter and setter method.
 *
 * @author Florence Tan
 */

public class UnitTestVo {

    // Spring will populate the following field(s) through Dependency Injection.
    private String testDatasetId;
    private String testHoleIdentifier;
    private String testLogIdType1;
    private String testLogIdType2;
    private String testLogIdMosaic;
    private String testLogIdTrayThumbnail;
    private String testLogIdTrayImage;
    private String testLogIdImagery;
    private String[] testLogIdList;
    private String testDomainlogId;

    public String getTestDatasetId() {
        return this.testDatasetId;
    }

    public void setTestDatasetId(String testDatasetId) {
        this.testDatasetId = testDatasetId;
    }

    public String getTestHoleIdentifier() {
        return this.testHoleIdentifier;
    }

    public void setTestHoleIdentifier(String testHoleIdentifier) {
        this.testHoleIdentifier = testHoleIdentifier;
    }

    public String getTestLogIdType1() {
        return this.testLogIdType1;
    }

    public void setTestLogIdType1(String testLogIdType1) {
        this.testLogIdType1 = testLogIdType1;
    }

    public String getTestLogIdType2() {
        return this.testLogIdType2;
    }

    public void setTestLogIdType2(String testLogIdType2) {
        this.testLogIdType2 = testLogIdType2;
    }

    public String getTestLogIdMosaic() {
        return this.testLogIdMosaic;
    }

    public void setTestLogIdMosaic(String testLogIdMosaic) {
        this.testLogIdMosaic = testLogIdMosaic;
    }

    public String getTestLogIdTrayThumbnail() {
        return this.testLogIdTrayThumbnail;
    }

    public void setTestLogIdTrayThumbnail(String testLogIdTrayThumbnail) {
        this.testLogIdTrayThumbnail = testLogIdTrayThumbnail;
    }

    public String getTestLogIdTrayImage() {
        return this.testLogIdTrayImage;
    }

    public void setTestLogIdTrayImage(String testLogIdTrayImage) {
        this.testLogIdTrayImage = testLogIdTrayImage;
    }

    public String getTestLogIdImagery() {
        return this.testLogIdImagery;
    }

    public void setTestLogIdImagery(String testLogIdImagery) {
        this.testLogIdImagery = testLogIdImagery;
    }

    public String[] getTestLogIdList() {
        return this.testLogIdList;
    }

    public void setTestLogIdList(String[] testLogIdList) {
        this.testLogIdList = testLogIdList;
    }

    public String getDomainlogId() {
        return this.testDomainlogId;
    }

    public void setTestDomainlogId(String testDomainlogId) {
        this.testDomainlogId = testDomainlogId;
    }
}
