package com.weather.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Component
public class WeatherResponseExceptionHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        if(response.getStatusCode().is4xxClientError()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Weather Data Not Found");
        }
    }
}
