package com.springboot.template.core.security;

import com.springboot.template.core.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationContext {
    public Long authenticatedId() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(Long.class::cast)
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED));
    }
}
