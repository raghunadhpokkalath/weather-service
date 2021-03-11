package com.weather.api.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.weather.api.config.ApiConfig
import com.weather.api.exception.WeatherResponseExceptionHandler
import com.weather.api.repository.WeatherDataRepository
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class WeatherServiceImplTest extends Specification {
    def weatherDataRepository
    def WeatherServiceImpl weatherServiceImpl
    def ApiConfig apiConfig
    def WeatherResponseExceptionHandler weatherResponseExceptionHandler
    def RestTemplate restTemplate
    def JsonNode weatherNode
    def setup() {
        weatherDataRepository = Mock(WeatherDataRepository)
        apiConfig = new ApiConfig(url:"http://locahost")
        restTemplate = Mock(RestTemplate)
        weatherResponseExceptionHandler = Mock(WeatherResponseExceptionHandler)
        def weatherData = "{\"weather\":[{\"id\": 804,\"main\": \"Clouds\",\"description\": \"overcast clouds\",\"icon\": \"04d\"}]}"
        def ObjectMapper mapper = new ObjectMapper()
        weatherNode = mapper.readTree(weatherData)
        weatherServiceImpl = new WeatherServiceImpl(weatherDataRepository: weatherDataRepository, apiConfig: apiConfig, weatherResponseExceptionHandler: weatherResponseExceptionHandler,restTemplate:restTemplate)
    }


    def "Retrieves Weather Description for Valid Country and City"() {
        given:
        def city = "Melbourne"
        def country = "Australia"
        def apiKey = "valid.key"
        when:
        def weatherResponse = weatherServiceImpl.getData(country, city, apiKey)

        then:
        1 * weatherDataRepository.findWeatherDataByCountryAndCity(country, city) >> null
        1 * restTemplate.getForObject("http://locahost?q=Melbourne,Australia&appid=valid.key", JsonNode.class) >> weatherNode
        1 * weatherDataRepository.save(_)

    }

}
