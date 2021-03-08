package com.weather.api.controller;

import com.weather.api.dto.WeatherResponse;
import com.weather.api.service.WeatherService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.time.Duration;

@Api(value = "Weather Data  Retrieval")
@RestController
@Validated
public class WeatherController {

    private final Bucket bucket;
    @Autowired
    WeatherService weatherService;

    public WeatherController() {
        Bandwidth limit = Bandwidth.classic(2, Refill.greedy(2, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();


    }

    @GetMapping("/data/description")
    @ApiOperation(value = "Retrieve Weather Description", response = WeatherResponse.class, notes = "This endpoint retrieve Weather Description based on Country And City")
    @ApiResponses({@ApiResponse(code = 200, message = "Weather Description"),
            @ApiResponse(code = 400, message = "Invalid Request Parameters"),
            @ApiResponse(code = 500, message = "Internal Server Error")})

    public ResponseEntity<WeatherResponse> getWeatherDescription(
            @ApiParam("Country Name") @RequestParam @NotEmpty(message = "Country  is Mandatory") String county, @ApiParam("City Name") @RequestParam @NotEmpty(message = "City  is Mandatory") String city,
            @ApiParam("API Key") @RequestHeader(name = "x-api-key") String apiKey) {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(weatherService.getData(county, city, apiKey));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();

    }

}
