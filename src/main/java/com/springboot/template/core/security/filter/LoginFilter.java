package com.springboot.template.core.security.filter;

import com.google.gson.Gson;
import com.springboot.template.core.security.SecurityParams;
import com.springboot.template.core.security.dto.AuthenticatedUser;
import com.springboot.template.core.security.dto.LoginRequest;
import com.springboot.template.core.security.dto.LoginResponse;
import com.springboot.template.util.GsonUtils;
import com.springboot.template.util.IOUtils;
import com.springboot.template.util.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private SecurityParams securityParams;

    public LoginFilter(AuthenticationManager authenticationManager, SecurityParams securityParams) {
        this.authenticationManager = authenticationManager;
        this.securityParams = securityParams;
        super.setFilterProcessesUrl("/login");
    }

    private UsernamePasswordAuthenticationToken getLoginRequest(HttpServletRequest request) {
        return IOUtils.requestBodyString(request)
                .map(body -> GsonUtils.castJson(body, LoginRequest.class))
                .map(LoginRequest::getToken)
                .orElse(new UsernamePasswordAuthenticationToken(null, null, null));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        UsernamePasswordAuthenticationToken passwordAuthenticationToken = getLoginRequest(req);
        return authenticationManager.authenticate(passwordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException {
        Instant expiration = ZonedDateTime.now().plusMinutes(securityParams.getExp()).toInstant();
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) auth.getPrincipal();

        Map<String, String> claims = new HashMap<>();
        claims.put("id", authenticatedUser.getId().toString());

        String token = JwtUtils.createJwtToken(expiration, securityParams.getSecret(), claims);
        response.addHeader(HttpHeaders.AUTHORIZATION, SecurityParams.TOKEN_PREFIX + token);

        buildResponseBody(response, authenticatedUser);
    }

    private void buildResponseBody(HttpServletResponse response, AuthenticatedUser authenticatedUser) throws IOException {
        LoginResponse loginResponse = new LoginResponse(authenticatedUser.getId());
        String json = new Gson().toJson(loginResponse);

        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
}
