package com.weather.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler  {
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(RecordNotFoundException.class)
//    public final ApiException handleNotFound(Exception ex) {
//        return new ApiException(ex.getMessage());
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public final ApiException handleRequestValidation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        return new ApiException(HttpStatus.BAD_REQUEST,"Empty Query Parameters",errors);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ResponseStatusException.class)
    public final ApiException handleUnAuthrorized(ResponseStatusException ex) {
        return new ApiException(HttpStatus.UNAUTHORIZED,ex.getReason());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotFoundException.class)
    public final ApiException handleNotFound(RecordNotFoundException ex) {
        return new ApiException(HttpStatus.NOT_FOUND,ex.getMessage());
    }
}
