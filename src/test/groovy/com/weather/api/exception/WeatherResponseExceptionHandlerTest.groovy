package com.weather.api.exception

import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.server.ResponseStatusException
import spock.lang.Specification

class WeatherResponseExceptionHandlerTest extends Specification {
    def WeatherResponseExceptionHandler weatherResponseExceptionHandler = new WeatherResponseExceptionHandler()
    def ClientHttpResponse clientHttpResponse

    def "Response Status Exception thrown for Not Found Error"() {
        given:
        def ClientHttpResponse clientHttpResponse = Mock(ClientHttpResponse)

        when:
        clientHttpResponse.getStatusCode() >> HttpStatus.NOT_FOUND
        def resp = weatherResponseExceptionHandler.handleError(clientHttpResponse)

        then:
        thrown(ResponseStatusException)
    }

    def "Return False for HandleError"() {

        given:
        def ClientHttpResponse clientHttpResponse = Mock(ClientHttpResponse)

        when:
        def resp = weatherResponseExceptionHandler.hasError(clientHttpResponse)

        then:
        resp == false


    }
}
