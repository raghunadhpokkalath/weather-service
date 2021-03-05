package com.weather.api.repository;

import com.weather.api.model.WeatherData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherDataRepository extends CrudRepository<WeatherData,String> {

    List<WeatherData> findByCountry(String country);
}
