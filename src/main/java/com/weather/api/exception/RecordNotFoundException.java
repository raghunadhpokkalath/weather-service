package com.weather.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Weather Data Not Found")
public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String message){
        super(message);
    }

}
