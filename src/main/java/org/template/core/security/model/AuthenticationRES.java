package org.template.core.security.model;

public class AuthenticationRES {

    private final String token;

    public AuthenticationRES(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
