package org.template.core.security;


import org.template.core.openapi.InternalServerError;
import org.template.core.openapi.Ok;
import org.template.core.openapi.Unauthorized;
import org.template.core.security.model.AuthenticationREQ;
import org.template.core.security.model.AuthenticationRES;
import org.template.util.JwtUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/authentication")
@ConditionalOnProperty(name = {"app.security.jwt-secret", "app.security.jwt-expiration-time-minutes"})
@ConditionalOnBean(AuthenticationManager.class)
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    @Value("${app.security.jwt-secret}")
    private String jwtSecret;

    @Value("${app.security.jwt-expiration-time-minutes}")
    private Integer expirationMinutes;

    public AuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    @Ok
    @Unauthorized
    @InternalServerError
    public AuthenticationRES createAuthenticationToken(@RequestBody AuthenticationREQ authenticationREQ) {
        UsernamePasswordAuthenticationToken userAndPassword
                = new UsernamePasswordAuthenticationToken(authenticationREQ.getUserName(), authenticationREQ.getPassword());

        Authentication authentication = authenticationManager.authenticate(userAndPassword);
        User user = (User) authentication.getPrincipal();

        Instant expiration = ZonedDateTime.now().plusMinutes(expirationMinutes).toInstant();
        Map<String, String> claims = new HashMap<>();
        claims.put("id", user.getUsername());

        final String token = JwtUtils.createJwtToken(expiration, jwtSecret, claims);
        return new AuthenticationRES(token);
    }
}
