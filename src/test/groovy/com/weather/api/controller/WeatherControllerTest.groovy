package com.weather.api.controller

import com.weather.api.dto.WeatherResponse
import com.weather.api.exception.RecordNotFoundException
import com.weather.api.service.WeatherService
import org.springframework.http.HttpStatus
import spock.lang.Specification

class WeatherControllerTest extends Specification {

    WeatherService weatherService
    WeatherController controller;

    def setup() {
        weatherService = Mock(WeatherService)
        controller = new WeatherController(weatherService: weatherService)
    }

    def "Successfully retrieves Weather Description for Valid Country and City"() {

        given:
        def city = "Melbourne"
        def country = "Australia"
        def apiKey = "xxxx"


        when:
        def weatherResponse = controller.getWeatherDescription(country, city, apiKey)

        then:
        1 * weatherService.getData(country, city, apiKey) >> new WeatherResponse("few clouds")
        weatherResponse.statusCode == HttpStatus.OK
        weatherResponse.getBody().description == 'few clouds'


    }


    def "RecordNotFoundException for invalid country and  City"() {

        given:
        def city = "Melbourne"
        def country = "asdasda"
        def apiKey = "xxxx"


        when:
        def weatherResponse = controller.getWeatherDescription(country, city, apiKey)

        then:
        1 * weatherService.getData(country, city, apiKey) >> { throw new RecordNotFoundException("Weather Data Not Found") }
        thrown RecordNotFoundException.class

    }


}


