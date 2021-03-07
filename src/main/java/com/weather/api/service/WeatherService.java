package com.weather.api.service;

import com.weather.api.dto.WeatherResponse;

public interface WeatherService {
    /*This will retrieve the Weather Data based on Country and City */
    WeatherResponse getData(String country, String city, String apiKey);


}
