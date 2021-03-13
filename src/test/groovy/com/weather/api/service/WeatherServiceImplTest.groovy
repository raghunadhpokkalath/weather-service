package com.weather.api.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.weather.api.config.ApiConfig
import com.weather.api.dto.WeatherResponse
import com.weather.api.exception.RecordNotFoundException
import com.weather.api.exception.WeatherResponseExceptionHandler
import com.weather.api.model.WeatherData
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
    def ObjectMapper mapper

    def setup() {
        weatherDataRepository = Mock(WeatherDataRepository)
        def keys  = new ArrayList()
        keys.add("key1")
        keys.add("key2")
        apiConfig = new ApiConfig(url: 'http://locahost',keys:keys)
        restTemplate = Mock(RestTemplate)
        weatherResponseExceptionHandler = Mock(WeatherResponseExceptionHandler)
        mapper = new ObjectMapper()

        weatherServiceImpl = new WeatherServiceImpl(weatherDataRepository: weatherDataRepository, apiConfig: apiConfig, weatherResponseExceptionHandler: weatherResponseExceptionHandler, restTemplate: restTemplate)
    }

    def "Return Weather Description Successfully for Valid Country and City"() {
        given:
        def city = 'Melbourne'
        def country = 'Australia'
        def apiKey = 'apiKey'
        def weatherData = "{\"weather\":[{\"id\": 804,\"main\": \"Clouds\",\"description\": \"overcast clouds\",\"icon\": \"04d\"}]}"
        weatherNode = mapper.readTree(weatherData)
        def expectedResponse = new WeatherResponse('overcast clouds')
        when:
        def actualResponse = weatherServiceImpl.getData(country, city, apiKey)

        then:
        1 * weatherDataRepository.findWeatherDataByCountryAndCity(country, city) >> null
        1 * restTemplate.getForObject('http://locahost?q=Melbourne,Australia&appid=apiKey', JsonNode.class) >> weatherNode
        1 * weatherDataRepository.save(_)
        expectedResponse.description == actualResponse.description
    }

    def "Throw Record Not Found Exception for Invalid Country or City Code"() {
        given:
        def city = 'Melbourne11'
        def country = 'Australia111'
        def apiKey = 'apiKey'
        def weathermapResp = "{\"cod\":\"404\",\"message\": \"city not found\"}"
        weatherNode = mapper.readTree(weathermapResp)

        when:
        weatherServiceImpl.getData(country, city, apiKey)

        then:
        1 * weatherDataRepository.findWeatherDataByCountryAndCity(country, city) >> null
        1 * restTemplate.getForObject('http://locahost?q=Melbourne11,Australia111&appid=apiKey', JsonNode.class) >> weatherNode
        0 * weatherDataRepository.save(_)
        thrown(RecordNotFoundException.class)
    }

    def "Query Data from  DB if present and  dont invoke OpenWeatherMap API"() {
        given:
        def city = 'Melbourne'
        def country = 'Australia'
        def apiKey = 'apiKey'
        def actualData = new WeatherData(country, city, 'overcast clouds')
        def expectedServiceResponse = new WeatherResponse('overcast clouds')
        def expectedDBData = new WeatherData(country, city, 'overcast clouds')

        when:
        def actualServiceResp = weatherServiceImpl.getData(country, city, apiKey)

        then:
        1 * weatherDataRepository.findWeatherDataByCountryAndCity(country, city) >> actualData
        0 * restTemplate.getForObject('http://locahost?q=Melbourne11,Australia111&appid=apiKey', JsonNode.class) >> weatherNode
        0 * weatherDataRepository.save(_)
        actualServiceResp.description == expectedServiceResponse.description
        actualData.description == expectedDBData.description
        actualData.country == expectedDBData.country
        actualData.city == expectedDBData.city
    }

}
