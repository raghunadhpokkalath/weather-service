package com.weather.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Weather {
    String id;
    String main;
    String description;
    String icon;
}
