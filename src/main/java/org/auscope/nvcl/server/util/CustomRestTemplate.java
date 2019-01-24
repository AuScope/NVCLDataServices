package org.auscope.nvcl.server.util;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class CustomRestTemplate extends RestTemplate {


    public CustomRestTemplate() {
        super();
    }

    public CustomRestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, final ResponseExtractor<T> responseExtractor)
            throws RestClientException {

        return super.doExecute(url, method, requestCallback, new ResponseExtractor<T>() {
            public T extractData(ClientHttpResponse response) throws IOException {
                    response.getHeaders().setContentType(MediaType.TEXT_XML);

                return responseExtractor.extractData(response);
            }
        });
    }
}
