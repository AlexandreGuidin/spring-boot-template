package com.springboot.template.security.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collections;

public class LoginRequest {

    private String email;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @ApiIgnore
    public UsernamePasswordAuthenticationToken getToken() {
        return new UsernamePasswordAuthenticationToken(email, password, Collections.emptyList());
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
