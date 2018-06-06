package com.maryanto.dimas.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

public class SecurityCustomeRestTemplateHeaderAuthentication implements ClientHttpRequestInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    private static final Logger console = LoggerFactory.getLogger(SecurityCustomeRestTemplateHeaderAuthentication.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        console.info("security cridential : {}", authentication);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "");
        return null;
    }
}
