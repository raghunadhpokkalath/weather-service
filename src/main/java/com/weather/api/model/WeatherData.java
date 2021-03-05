package com.weather.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class WeatherData {

    private String description;
    private String country;
    private String city;
    @Id
    private Long id;
}
