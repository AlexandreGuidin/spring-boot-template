package org.template.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.template.core.exception.ApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.zip.GZIPInputStream;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

public class RestTemplateErrorHandler implements ResponseErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestTemplateErrorHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().series() == CLIENT_ERROR || httpResponse.getStatusCode().series() == SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse response) {
        logger.error(MessageFormat.format("unhandled exception at RestTemplate. response: {0}", response.toString()));
        throw new ApiException(false);
    }


    private String decodeGzipResponse(InputStream responseInputStream) {
        try {
            return new String(new GZIPInputStream(responseInputStream).readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse httpResponse) {
        try {
            InputStream responseInputStream = httpResponse.getBody();

            String decodedResponse = decodeGzipResponse(responseInputStream);
            decodedResponse = (decodedResponse == null) ? new String(responseInputStream.readAllBytes(), StandardCharsets.UTF_8) : decodedResponse;

            String message = MessageFormat.format("error {0} while calling url: {1} and receive response: {2}",
                    httpResponse.getRawStatusCode(),
                    url.toString(),
                    decodedResponse);
            logger.error(message);
            throw new ApiException(false);
        } catch (IOException ex) {
            logger.error(MessageFormat.format("error while calling url: {0}", url.toString()), ex);
            throw new ApiException(false);
        }
    }
}
