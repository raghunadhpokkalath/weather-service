package com.weather.api.config;

import com.weather.api.Interceptor.ApiKeyInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@Slf4j
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public ApiKeyInterceptor apiKeyValidator() {
        return new ApiKeyInterceptor();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        log.debug("addInterceptors method");
        registry.addInterceptor(apiKeyValidator()).excludePathPatterns(
                        "/v3/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**"
                );
        WebMvcConfigurer.super.addInterceptors(registry);

    }
}
