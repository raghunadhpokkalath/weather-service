package com.weather.api.Interceptor

import com.weather.api.config.ApiConfig
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ApiKeyInterceptorTest extends Specification {
    def  ApiKeyInterceptor apiKeyInterceptor
    def HttpServletRequest request
    def HttpServletResponse response
    def ApiConfig apiConfig

    def setup() {
        def keys  = new ArrayList()
        keys.add("validkey1")
        keys.add("validkey2")
        apiConfig = new ApiConfig(url: 'http://locahost',keys:keys)
        apiKeyInterceptor = new ApiKeyInterceptor(apiConfig:apiConfig)
        request = Mock(HttpServletRequest)
        response = Mock(HttpServletResponse)
    }
    def "Should Return True for Valid ApiKeys"() {
        when:
        request.getHeader("x-api-key") >> "validkey1"
        def result = apiKeyInterceptor.preHandle(request,response,new Object())
        then:
        result == true
    }

    def "Should throw Unauthorised for Invalid ApiKeys"() {

        when:
        request.getHeader("x-api-key") >> "invalidkey"
        def result = apiKeyInterceptor.preHandle(request,response,new Object())

        then:
        thrown(ResponseStatusException)
    }
}
