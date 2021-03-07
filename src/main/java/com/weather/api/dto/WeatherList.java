package com.weather.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class WeatherList {
    private List<Weather> weatherList;

    public WeatherList(){
        weatherList = new ArrayList<>();
    }
}
