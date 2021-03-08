package com.weather.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
@ToString
@Getter
@Setter
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames ={"country","city"})})
public class WeatherData {

    private String description;
    private String country;
    private String city;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public WeatherData(){

    }

    public WeatherData(String country,String city,String description){
        this.country = country;
        this.city = city;
        this.description = description;
    }
}
