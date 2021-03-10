package com.weather.api.Interceptor;

import com.weather.api.config.ApiConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class ApiKeyValidator implements HandlerInterceptor {


    //@Value("${api.valid.keys}}")
    //private List<String> apiKeys;

    @Autowired
    ApiConfig apiConfig;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        String headerApiKey = request.getHeader("x-api-key");
        log.info("ApiKeyValidator preHandle valid apikeys {}", apiConfig.getKeys());
        if (!apiConfig.getKeys().contains(headerApiKey.trim())) {
            log.info("Api Key in Header Not matching with Valid API Keys {}",headerApiKey);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Api Key");
        }
        return true;
    }
}
