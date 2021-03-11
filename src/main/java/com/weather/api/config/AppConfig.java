package com.weather.api.config;

import com.weather.api.Interceptor.ApiKeyValidator;
import com.weather.api.exception.WeatherResponseExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@Slf4j
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public ApiKeyValidator apiKeyValidator() {
        return new ApiKeyValidator();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        log.debug("addInterceptors method");
        registry.addInterceptor(apiKeyValidator());
        WebMvcConfigurer.super.addInterceptors(registry);

    }
}
