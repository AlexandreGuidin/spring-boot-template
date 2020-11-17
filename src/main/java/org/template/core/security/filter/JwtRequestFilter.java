package org.template.core.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;

import org.template.util.JwtUtils;
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

public class JwtRequestFilter extends BasicAuthenticationFilter {

    private final String secret;

    public JwtRequestFilter(AuthenticationManager authenticationManager, String secret) {
        super(authenticationManager);
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        header = header.replace("Bearer ", "");
        manageAuthentication(header);
        chain.doFilter(req, res);
    }

    private void manageAuthentication(String token) {
        DecodedJWT decodedToken = JwtUtils.decodeToken(token, secret);

        String id = Optional.ofNullable(decodedToken)
                .map(d -> d.getClaim("id").asString())
                .orElse(null);

        UsernamePasswordAuthenticationToken authentication = Optional.ofNullable(id)
                .map(t -> new UsernamePasswordAuthenticationToken(t, null, Collections.emptyList()))
                .orElse(null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
