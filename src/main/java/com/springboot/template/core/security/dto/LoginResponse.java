package com.springboot.template.core.security.dto;

public class LoginResponse {

    private Long id;

    public LoginResponse() {
    }

    public LoginResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LoginResponse setId(Long id) {
        this.id = id;
        return this;
    }
}
