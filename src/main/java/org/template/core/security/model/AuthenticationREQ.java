package org.template.core.security.model;

public class AuthenticationREQ {

    private String userName;
    private String password;

    public AuthenticationREQ() {
    }

    public AuthenticationREQ(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public AuthenticationREQ setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthenticationREQ setPassword(String password) {
        this.password = password;
        return this;
    }
}
