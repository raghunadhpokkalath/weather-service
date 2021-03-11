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

import java.util.Optional;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {

  @Autowired
  WeatherResponseExceptionHandler weatherResponseExceptionHandler;

  @Autowired
  WeatherDataRepository weatherDataRepository;

  @Autowired
  ApiConfig apiConfig;

  @Override
  public WeatherResponse getData(String country, String city, String apiKey) {
    WeatherData weatherData =
        Optional.ofNullable(weatherDataRepository.findWeatherDataByCountryAndCity(country, city))
            .orElseGet(() -> getDataFromAPI(country, city, apiKey));
    WeatherResponse weatherResponse = new WeatherResponse(weatherData.getDescription());
    return weatherResponse;
  }

  private WeatherData getDataFromAPI(String country, String city, String apiKey) {
    RestTemplate restTemplate =
        new RestTemplateBuilder().errorHandler(weatherResponseExceptionHandler).build();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiConfig.getUrl());
    builder.queryParam("q", city + "," + country).queryParam("appid", apiKey).build();
    log.info("Invoking API Endpoint {}", builder.toUriString());
    JsonNode responseNode = restTemplate.getForObject(builder.toUriString(), JsonNode.class);
    log.info("API Response {}", responseNode);
    JsonNode weatherNode =
        Optional.ofNullable(responseNode.get("weather")).orElseThrow(RecordNotFoundException::new);
    String description =
        Optional.ofNullable(weatherNode.get(0).get("description").asText())
            .orElseThrow(RecordNotFoundException::new);
    WeatherData data = new WeatherData(country, city, description);
    weatherDataRepository.save(data);
    return data;
  }
}
