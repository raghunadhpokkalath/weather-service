package com.weather.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ApiException {

    private String message;
    private HttpStatus status;
    private List<String> errors;

    public ApiException(HttpStatus status, String message, List<String> errors){
        this.message = message;
        this.status=status;
        this.errors=errors;

    }

    public ApiException(HttpStatus status, String message){
        this.message = message;
        this.status=status;
    }
}
