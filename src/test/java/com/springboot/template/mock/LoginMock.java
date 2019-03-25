package com.springboot.template.mock;

import com.springboot.template.security.dto.AuthenticatedUser;

public class LoginMock {

    private LoginMock() {
    }

    public static AuthenticatedUser mockAuthenticated() {
        return new AuthenticatedUser(UserMock.mock());
    }
}
