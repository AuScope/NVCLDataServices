package org.auscope.nvcl.regression;

public final class RegressionConfig {

    private RegressionConfig() {
    }

    public static final String DEV = "http://localhost:8080/NVCLDataServices";

    public static final String PROD = "https://nvclwebservices.csiro.au/NVCLDataServices";

    public static final int TIMEOUT_SECONDS = 60;
}