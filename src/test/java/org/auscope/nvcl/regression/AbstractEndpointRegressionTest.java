package org.auscope.nvcl.regression;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractEndpointRegressionTest {

    protected static final EndpointClient client = new EndpointClient();

    static {

        try {

            initialiseContext();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to initialise regression context",
                    e);
        }
    }

    private static void initialiseContext()
            throws Exception {

        if (RegressionState.CONTEXT.getDatasetId() != null) {
            return;
        }

        String jsonheaders = client.get(
                RegressionConfig.DEV + "/getDatasetCollection.html?holeidentifier=all&outputformat=json");

        ObjectMapper mapper = new ObjectMapper();

        JsonNode rootheaders = mapper.readTree(jsonheaders);

        JsonNode dataset = rootheaders.path("datasetCollection").get(0);

        RegressionState.CONTEXT.setDatasetId(dataset.path("datasetID").asText());

        String json = client.get(
                RegressionConfig.DEV + "/getDatasetCollection.html?datasetid=" + RegressionState.CONTEXT.getDatasetId()
                        + "&outputformat=json");

        JsonNode root = mapper.readTree(json);

        JsonNode datasetfull = root.path("datasetCollection").get(0);

        JsonNode imageLogs = datasetfull.path("imageLogCollection").path("imageLogCollection");

        JsonNode spectralLogs = datasetfull.path("spectralLogCollection").path("SpectralLogCollection");

        for (JsonNode imglog : imageLogs) {

            String logName = imglog.path("logName").asText();

            if (logName.contains("Tray Images")) {
                RegressionState.CONTEXT.setTrayLogId(imglog.path("logID").asText());
                break;
            }

        }

        JsonNode logs = datasetfull.path("logCollection").path("logCollection");

        for (JsonNode log : logs) {

            String logName = log.path("logName").asText();

            if (logName.contains("Min1")) {
                RegressionState.CONTEXT.setClassLogId(log.path("logID").asText());
                break;
            }
        }
        for (JsonNode log : logs) {

            String logName = log.path("logName").asText();

            if (logName.contains("Wt1")) {
                RegressionState.CONTEXT.setDecimalLogId(log.path("logID").asText());
                break;
            }
        }

        for (JsonNode log : logs) {

            int logtype = log.path("logType").asInt();

            if (logtype == 6) {
                RegressionState.CONTEXT.setLogIdSet(RegressionState.CONTEXT.getClassLogId() + ","
                        + RegressionState.CONTEXT.getDecimalLogId() + "," + log.path("logID").asText());
                break;
            }
        }

        for (JsonNode speclog : spectralLogs) {

            String logName = speclog.path("logName").asText();

            if (logName.contains("Reflectance")) {
                RegressionState.CONTEXT.setSpectralLogId(speclog.path("logID").asText());
                break;
            }
        }

    }

    protected String devUrl(
            String relativeUrl) {

        return RegressionConfig.DEV + relativeUrl;
    }

    protected String prodUrl(
            String relativeUrl) {

        return RegressionConfig.PROD + relativeUrl;
    }
}