package com.weather.api.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Slf4j
public class ApiKeyValidator implements HandlerInterceptor {


    @Value("${api.valid.keys}}")
    private List<String> apiKeys;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        log.info("ApiKeyValidator preHandle valid apikeys {}", apiKeys);
        if (!apiKeys.contains(request.getHeader("x-api-key"))) {
            log.info("Api Key Not matching with Valid API Keys");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Api Key");
        }
        return true;
    }
}
