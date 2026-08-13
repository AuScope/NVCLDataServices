package org.auscope.nvcl.regression;

import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

public final class XmlComparisonHelper {

    private XmlComparisonHelper() {
    }

    public static Diff compare(
            String expected,
            String actual) {

        return DiffBuilder.compare(expected)
                .withTest(actual)
                .ignoreWhitespace()
                .checkForSimilar()
                .build();
    }
}