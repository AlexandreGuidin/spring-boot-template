package com.springboot.template.core.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.springboot.template.core.security.SecurityParams;
import com.springboot.template.util.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final SecurityParams securityParams;

    public AuthorizationFilter(AuthenticationManager authenticationManager, SecurityParams securityParams) {
        super(authenticationManager);
        this.securityParams = securityParams;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith(SecurityParams.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        header = header.replace(SecurityParams.TOKEN_PREFIX, "");
        manageAuthentication(header);
        chain.doFilter(req, res);
    }

    private void manageAuthentication(String token) {
        DecodedJWT decodedToken = JwtUtils.decodeToken(token, securityParams.getSecret());
        Long id = decodedToken.getClaim("id").as(Long.class);

        UsernamePasswordAuthenticationToken authentication = Optional.ofNullable(decodedToken)
                .map(t -> new UsernamePasswordAuthenticationToken(t, null, Collections.emptyList()))
                .orElse(null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}