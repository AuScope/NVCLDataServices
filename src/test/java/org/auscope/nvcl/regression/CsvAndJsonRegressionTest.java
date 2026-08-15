package org.auscope.nvcl.regression;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.opencsv.CSVReader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class CsvAndJsonRegressionTest
        extends AbstractEndpointRegressionTest {

    record CsvAndJsonEndpoint(
            String name,
            String url) {
    }

    static Stream<RegressionTestCase> endpoints() {

        return Stream.of(

                new CsvAndJsonEndpoint(
                        "Download Scalars", "/downloadscalars.html?logid=" + RegressionState.CONTEXT.getLogIdSet()),

                new CsvAndJsonEndpoint(
                        "Download Scalars 1m to 2m",
                        "/downloadscalars.html?logid=" + RegressionState.CONTEXT.getLogIdSet() + "&startdepth=1&enddepth=2"),
                new CsvAndJsonEndpoint(
                        "Download downsampled to 1m class type Scalar",
                        "/getDownsampledData.html?logid=" + RegressionState.CONTEXT.getClassLogId() + "&interval=1"),
                
                new CsvAndJsonEndpoint(
                        "Download downsampled to maximum class type Scalar",
                        "/getDownsampledData.html?logid=" + RegressionState.CONTEXT.getClassLogId() + "&interval=999999"),
                
                new CsvAndJsonEndpoint(
                        "Download downsampled to 1m decimal type Scalar",
                        "/getDownsampledData.html?logid=" + RegressionState.CONTEXT.getDecimalLogId() + "&interval=1"),
                
                new CsvAndJsonEndpoint(
                        "Download downsampled to maximum decimal type Scalar",
                        "/getDownsampledData.html?logid=" + RegressionState.CONTEXT.getDecimalLogId() + "&interval=999999"),

                new CsvAndJsonEndpoint(
                        "Download best TSA results",
                        "/getBestTSAResults.html?datasetid=" + RegressionState.CONTEXT.getDatasetId())
                        

                )

                .flatMap(endpoint -> Stream.of(

                        new RegressionTestCase(
                                endpoint.name() + " JSON",
                                endpoint.url()
                                        + "&outputformat=json",
                                ResponseType.JSON),

                        new RegressionTestCase(
                                endpoint.name() + " CSV",
                                endpoint.url()
                                        + "&outputformat=csv",
                                ResponseType.CSV)));
    }

    private String filter(String responseString) {
        // no filter yet, but this is where we would filter out any values that are expected to change between runs, such as timestamps or modified dates
        return responseString; 
        //.replaceAll("https?://[^\\s<>\"']+\\.zip", "{ZIP_URL}");

    }

    private List<String[]> parseCsv(String csv)
        throws Exception {

        try (CSVReader reader =
                new CSVReader(
                        new StringReader(csv))) {

            return reader.readAll();
        }
    }

    private void compareValues(String expected,String actual) {

    try {

        double d1 =
                Double.parseDouble(expected);

        double d2 =
                Double.parseDouble(actual);

        assertEquals(
                d1,
                d2,
                0.00001);

    } catch (NumberFormatException ex) {

        assertEquals(
                expected,
                actual);
    }
}

    private void compareCsv(
        String name,
        String prod,
        String dev,
        Set<String> IGNORED_NODES)
        throws Exception {

        List<String[]> prodRows = parseCsv(prod);

        List<String[]> devRows = parseCsv(dev);

        assertEquals(
                prodRows.size(),
                devRows.size(),
                name + " row count");

        for (int row = 0; row < prodRows.size(); row++) {

                String[] prodValues = prodRows.get(row);
                String[] devValues = devRows.get(row);

                assertEquals(
                        prodValues.length,
                        devValues.length,
                        "Column count differs on row " + row);

                for (int col = 0; col < prodValues.length; col++) {

                        compareValues(
                                prodValues[col],
                                devValues[col]);
                }
        }

    }

    private void compareJson(
        String name,
        String prod,
        String dev,
        Set<String> IGNORED_NODES) {

        assertThatJson(dev)
                .whenIgnoringPaths(IGNORED_NODES.toArray(new String[0]))
                .withTolerance(0.00001)
                .isEqualTo(prod);
    }

    @ParameterizedTest
    @MethodSource("endpoints")
    void responseShouldMatchProduction(
            RegressionTestCase tc)
            throws Exception {

        String prod = filter(client.get(
                prodUrl(
                        tc.relativeUrl())));

        String dev = filter(client.get(
                devUrl(
                        tc.relativeUrl())));

        final Set<String> IGNORED_NODES = Set.of(
                "[*].roundedDepth",
                "timestamp");

        switch (tc.responseType()) {

            case CSV -> compareCsv(
                    tc.name(),
                    prod,
                    dev,
                    IGNORED_NODES);

            case JSON -> compareJson(
                    tc.name(),
                    prod,
                    dev,
                    IGNORED_NODES);
        }

    }
}