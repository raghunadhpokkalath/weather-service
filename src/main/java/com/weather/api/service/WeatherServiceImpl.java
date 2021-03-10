package com.weather.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.weather.api.config.ApiConfig;
import com.weather.api.dto.WeatherResponse;
import com.weather.api.exception.RecordNotFoundException;
import com.weather.api.exception.WeatherResponseExceptionHandler;
import com.weather.api.model.WeatherData;
import com.weather.api.repository.WeatherDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    WeatherResponseExceptionHandler weatherResponseExceptionHandler;

    @Autowired
    WeatherDataRepository weatherDataRepository;

  //  @Value("${api.openweathermap.url}")
    //private String apiEndpoint;
    @Autowired
    ApiConfig apiConfig;

    @Override
    public WeatherResponse getData(String country, String city, String apiKey) {
        WeatherData weatherData = weatherDataRepository.findWeatherDataByCountryAndCity(country, city);
        String description = null;
        if (weatherData == null) {
            log.info("No Record Present in DB invoking API");
            description = getDataFromAPI(country, city, apiKey);
            WeatherData data = new WeatherData(country, city, description);
            log.debug("Saving Weather Details to DB");
            weatherDataRepository.save(data);
        } else {
            description = weatherData.getDescription();
            log.info("Retrieving Data from H2 DB description {}", description);

        }
        WeatherResponse weatherResponse = new WeatherResponse(description);
        return weatherResponse;
    }

    private String getDataFromAPI(String country, String city, String apiKey) {
        RestTemplate restTemplate = new RestTemplateBuilder().errorHandler(weatherResponseExceptionHandler).build();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiConfig.getUrl());
        builder.queryParam("q", city + "," + country).queryParam("appid", apiKey).build();
        log.info("Invoking API Endpoint {}", builder.toUriString());
        String description = null;
        JsonNode responseNode = restTemplate.getForObject(builder.toUriString(), JsonNode.class);
        log.info("API Response {}", responseNode);
        if (responseNode != null && responseNode.get("cod").asText().equals("200")) {
            JsonNode weatherNode = responseNode.get("weather");
            description = weatherNode.get(0).get("description").asText();
            log.info("Weather Description is {} for Country {} and City {}", description,country,city);

        } else if (responseNode.get("cod").asText().equals("404")) {
            throw new RecordNotFoundException("Weather Data not Found");
           // throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Weather Data not Found");
        }
        return description;
    }
}