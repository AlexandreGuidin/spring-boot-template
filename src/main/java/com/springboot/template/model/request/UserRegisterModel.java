package com.springboot.template.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserRegisterModel {

    @Email
    private String email;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}