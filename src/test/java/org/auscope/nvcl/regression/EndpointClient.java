package org.auscope.nvcl.regression;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class EndpointClient {

    private final HttpClient client =
            HttpClient.newBuilder()
                    .connectTimeout(
                            Duration.ofSeconds(
                                    RegressionConfig.TIMEOUT_SECONDS))
                    .build();

    public String get(String url)
            throws IOException, InterruptedException {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

        return client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString())
                .body();
    }

    public int getStatus(String url)
            throws IOException, InterruptedException {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

        return client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString())
                .statusCode();
    }

    public byte[] getBytes(String url)
        throws IOException, InterruptedException {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

        return client.send(
                        request,
                        HttpResponse.BodyHandlers.ofByteArray())
                .body();
        }
}