package com.weather.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeatherResponse {


    private String description;

    public WeatherResponse(String description){
        this.description=description;
    }
}
