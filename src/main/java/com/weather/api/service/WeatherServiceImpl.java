package com.weather.api.service;

import com.weather.api.dto.WeatherResponse;
import com.weather.api.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    WeatherDataRepository weatherDataRepository;

    //@Value("http://api.openweathermap.org/data/2.5")
    //private String weatherApi;

    WebClient client = WebClient.create("http://api.openweathermap.org/data/2.5");




    @Override
    public Mono<WeatherResponse> getData(String country, String city, String apiKey) {

        return client.get().uri(uriBuilder -> uriBuilder.path("/weather")
                .queryParam("q", country,city)
                .queryParam("appid", apiKey)
                .build()).retrieve().bodyToMono(WeatherResponse.class);
    }

}
