package com.weather.api.service;

import com.weather.api.dto.WeatherResponse;
import reactor.core.publisher.Mono;

public interface WeatherService {
    /*This will retrieve the Weather Data based on Country and City */
    Mono<WeatherResponse> getData(String country, String city,String apiKey);


}
