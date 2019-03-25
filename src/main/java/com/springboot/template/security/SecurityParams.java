package com.springboot.template.security;

import org.springframework.beans.factory.annotation.Value;

public class SecurityParams {

    public static String TOKEN_PREFIX = "Bearer ";

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.exp}")
    private Long exp;

    public static String getTokenPrefix() {
        return TOKEN_PREFIX;
    }

    public String getSecret() {
        return secret;
    }

    public Long getExp() {
        return exp;
    }
}
