package com.weather.api.controller;

import com.weather.api.dto.AccountDto;
import com.weather.api.service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Weather Data  Retrieval")
@RestController
public class WeatherController {

    @Autowired
    WeatherService weatherService;


    @GetMapping("/weather/description")
    @ApiOperation(value = "Retrive Weather Description" , response = WeatherResponse.class,notes="This endpoint retrieve Weather Description based on Country cide and city code ")
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "Weather Description"),
                    @ApiResponse(code = 400, message = "Invalid Request"),
                    @ApiResponse(code = 500, message = "Internal Server Error")
            }
    )
    @ResponseEntity
    public WeatherResponse getWeatherDescription(){

       return weatherService.getDescription();
    }

}
