package com.poli.meets.feedback.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;


@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String TOKEN_TYPE = "Bearer";

    private String jwtTokenStorage;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, jwtTokenStorage));
    }

    public void setJwtTokenStorage(String jwtTokenStorage) {
        this.jwtTokenStorage = jwtTokenStorage;
    }

}