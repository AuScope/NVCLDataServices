package org.auscope.nvcl.regression;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.xmlunit.diff.Diff;
import org.xmlunit.builder.DiffBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class ImportantEndpointsRegressionTest
                extends AbstractEndpointRegressionTest {

        static Stream<RegressionTestCase> endpoints() {

                return Stream.of(

                                Map.entry("Get all datasets", "/getDatasetCollection.html?holeidentifier=all"),

                                Map.entry("Get specific dataset",
                                                "/getDatasetCollection.html?datasetid="
                                                                + RegressionState.CONTEXT.getDatasetId()),

                                Map.entry("Get specific dataset with domains",
                                                "/getDatasetCollection.html?datasetid=" + RegressionState.CONTEXT.getDatasetId()
                                                                + "&includedomains=yes"),

                                Map.entry("Get specific dataset headersonly",
                                                "/getDatasetCollection.html?datasetid=" + RegressionState.CONTEXT.getDatasetId()
                                                                + "&headersonly=yes"),

                                Map.entry("Get tray image depths",
                                                "/getImageTrayDepth.html?logid="
                                                                + RegressionState.CONTEXT.getTrayLogId()),

                                Map.entry("Get spectral logs for specific dataset",
                                                "/getspectrallogs.html?datasetid=" + RegressionState.CONTEXT.getDatasetId()),

                                Map.entry("Get Algoirthms",
                                                "/getAlgorithms.html"),
                                
                                Map.entry("Get Log specific classifications",
                                                "/getClassifications.html?logid=" + RegressionState.CONTEXT.getClassLogId()),
                                
                                Map.entry("Get Algorithm specific classifications",
                                                "/getClassifications.html?algorithmoutputid=48"),

                                Map.entry("Get dataset depth range",
                                                "/getDatasetDepthRange.html?datasetid=" + RegressionState.CONTEXT.getDatasetId()),

                                Map.entry("is download available",
                                                "/isdownloadavailable.html?datasetid=" + RegressionState.CONTEXT.getDatasetId()),

                                Map.entry("get domain",
                                                "/getDomains.html?datasetid=" + RegressionState.CONTEXT.getDatasetId()),
                                                

                                Map.entry("Get Log Collection for specific dataset",
                                                "/getLogCollection.html?datasetid=" + RegressionState.CONTEXT.getDatasetId()))

                                .flatMap(endpoint -> Stream.of(

                                                new RegressionTestCase(
                                                                endpoint.getKey() + " (XML)",
                                                                endpoint.getValue(),
                                                                ResponseType.XML),

                                                new RegressionTestCase(
                                                                endpoint.getKey() + " (JSON)",
                                                                endpoint.getValue() + "&outputformat=json",
                                                                ResponseType.JSON)));
        }

        private String filterZipUrls(String xml) {

                return xml.replaceAll("https?://[^\\s<>\"']+\\.zip", "{ZIP_URL}");

        }

        private void compareXml(String testName,
                        String prod,
                        String dev,
                        Set<String> IGNORED_NODES) {

                Diff diff = DiffBuilder.compare(prod)
                                .withTest(dev)
                                .withNodeFilter(node -> !IGNORED_NODES.contains(
                                                node.getNodeName()))
                                .checkForSimilar()
                                .build();

                assertFalse(
                                diff.hasDifferences(),
                                () -> testName
                                                + " differs\n"
                                                + diff);
        }

        private void compareJson(
                        String testName,
                        String prod,
                        String dev,
                        Set<String> IGNORED_NODES) {

                assertThatJson(dev)
                                .whenIgnoringPaths("datasetCollection[*].createdDate",
                                                "datasetCollection[*].modifiedDate","timestamp")
                                .withTolerance(0.00001)
                                .as(testName)
                                .isEqualTo(prod);
        }

        @ParameterizedTest
        @MethodSource("endpoints")
        void responseShouldMatchProduction(
                        RegressionTestCase tc)
                        throws Exception {

                String prod = filterZipUrls(client.get(
                                prodUrl(tc.relativeUrl())));

                String dev = filterZipUrls(client.get(
                                devUrl(tc.relativeUrl())));

                final Set<String> IGNORED_NODES = Set.of(
                                "modifiedDate",
                                "createdDate",
                                "timestamp");

                switch (tc.responseType()) {

                        case XML -> compareXml(
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