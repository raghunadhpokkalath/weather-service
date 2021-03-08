package com.weather.api.repository;

import com.weather.api.model.WeatherData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherDataRepository extends CrudRepository<WeatherData, String> {

    WeatherData findWeatherDataByCountryAndCity(String country, String city);

}
