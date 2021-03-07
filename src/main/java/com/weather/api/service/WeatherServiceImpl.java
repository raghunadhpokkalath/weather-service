package com.weather.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.weather.api.dto.WeatherResponse;
import com.weather.api.exception.WeatherResponseExceptionHandler;
import com.weather.api.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    WeatherResponseExceptionHandler weatherResponseExceptionHandler;

    @Autowired
    WeatherDataRepository weatherDataRepository;

    @Value("${api.openweathermap.url}")
    private String apiEndpoint;

    @Override
    public WeatherResponse getData(String country, String city, String apiKey) {
        String description = getDataFromAPI(country,city,apiKey);
        WeatherResponse weatherResponse=new WeatherResponse(description);
        return weatherResponse;
    }
    private String getDataFromAPI(String country, String city, String apiKey) {
        RestTemplate restTemplate = new RestTemplateBuilder().errorHandler(weatherResponseExceptionHandler).build();
        System.out.println("Api endpoint"+apiEndpoint);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiEndpoint);
        builder.queryParam("q", country + "," + city).queryParam("appid", apiKey).build();
        System.out.println("Rest Template URL " + builder.toUriString());
        String description = null;
        JsonNode responseNode = restTemplate.getForObject(builder.toUriString(), JsonNode.class);
        if (responseNode != null && responseNode.get("cod").asText().equals("200")) {
            System.out.println("Response Node" + responseNode.get("cod").asText());
            JsonNode weatherNode = responseNode.get("weather");
            description = weatherNode.get(0).get("description").asText();
        } else if (responseNode.get("cod").asText().equals("404")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Weather Data not Found");
        }
        return description;
    }
}