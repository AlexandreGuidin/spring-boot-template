package org.template.core.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@ConditionalOnProperty(name = {"app.security.jwt-secret", "app.security.api-user-name", "app.security.api-pass"})
@ConditionalOnBean(AuthenticationManager.class)
public class AuthenticationService implements UserDetailsService {

    @Value("${app.security.api-user-name}")
    private String apiUserName;

    @Value("${app.security.api-pass}")
    private String apiPassword;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (userName.trim().equals(apiUserName.trim())) {
            return new User(apiUserName, apiPassword, new ArrayList<>());
        }
        throw new UsernameNotFoundException("user not found");
    }
}
