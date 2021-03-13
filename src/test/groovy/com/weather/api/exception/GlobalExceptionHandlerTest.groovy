package com.weather.api.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import spock.lang.Specification
import spock.lang.Subject

class GlobalExceptionHandlerTest extends Specification {
    @Subject
    def GlobalExceptionHandler handler

    def setup() {
        handler = new GlobalExceptionHandler()
    }

    
    def "Verify HandleUnAuthorized Method"() {
        given:
        def exception = new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Api Key");
        def expResp = new ApiException(HttpStatus.UNAUTHORIZED, "Invalid Api Key")

        when:
        def result = handler.handleUnAuthrorized(exception)

        then:
        result.message == expResp.message
        result.status == expResp.status
    }

    def "Verify handleNotFound Method"() {
        given:
        def exception = new RecordNotFoundException("Weather Data Not Found")
        def expResp = new ApiException(HttpStatus.NOT_FOUND, "Weather Data Not Found")

        when:
        def result = handler.handleNotFound(exception)

        then:
        result.message == expResp.message
        result.status == expResp.status

    }

}
