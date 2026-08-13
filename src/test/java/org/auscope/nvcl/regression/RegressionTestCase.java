package org.auscope.nvcl.regression;

enum ResponseType {
    XML,
    JSON,
    CSV,
    BINARY
}


public record RegressionTestCase(
        String name,
        String relativeUrl,
        ResponseType responseType) {
}