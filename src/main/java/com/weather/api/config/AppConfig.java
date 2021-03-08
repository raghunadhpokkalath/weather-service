package com.weather.api.config;

import com.weather.api.Interceptor.ApiKeyValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@Slf4j
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public ApiKeyValidator apiKeyValidator() {
        return new ApiKeyValidator();
    }
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        log.debug("addInterceptors method");
        registry.addInterceptor(apiKeyValidator());
        WebMvcConfigurer.super.addInterceptors(registry);

    }
}
