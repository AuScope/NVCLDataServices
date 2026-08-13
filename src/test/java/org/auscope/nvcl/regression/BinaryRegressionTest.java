package org.auscope.nvcl.regression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class BinaryRegressionTest extends AbstractEndpointRegressionTest{

    static Stream<RegressionTestCase> endpoints() {

        return Stream.of(

                new RegressionTestCase("Get first Tray Image and compare hash",
                        "/getImage.html?logid=" + RegressionState.CONTEXT.getTrayLogId() + "&sampleno=0",
                        ResponseType.BINARY),

                new RegressionTestCase("Get first 10 spectra from the reflectance log and compare hash",
                        "/getspectraldata.html?speclogid=" + RegressionState.CONTEXT.getSpectralLogId() + "&startsampleno=0&endsampleno=9",
                        ResponseType.BINARY),


                new RegressionTestCase("Get tray map and compare hash",
                        "/gettraymap.html?logid=" + RegressionState.CONTEXT.getClassLogId() + "&trayindex=0",
                        ResponseType.BINARY)

                        

                // add a test for getprofdata.html. Note: the RKD datasets dont have prof data, so we need to find a dataset that has prof data to test this endpoint.
        );
    }

    private String sha256(byte[] data)
        throws Exception {

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hash =
                md.digest(data);

        return HexFormat.of()
                .formatHex(hash);
    }

    @ParameterizedTest
        @MethodSource("endpoints")
        void responseShouldMatchProduction( RegressionTestCase tc)
            throws Exception {


        byte[] prod = client.getBytes(prodUrl(tc.relativeUrl()));

        byte[] dev = client.getBytes(devUrl(tc.relativeUrl()));

        assertEquals(sha256(prod),sha256(dev));
    }
    
}
