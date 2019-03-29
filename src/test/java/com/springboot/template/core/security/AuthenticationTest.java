package com.springboot.template.core.security;

import com.springboot.template.Application;
import com.springboot.template.core.BaseControllerTest;
import com.springboot.template.util.JwtUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class AuthenticationTest extends BaseControllerTest {


    @Test
    public void tokenOK() {
        String token = JwtUtils.createJwtToken(Instant.now().plusSeconds(60), "V4CYY1ZzMH6g6tT2GqOtYABiieKuoHci", Collections.singletonMap("id", "1"));

        request("/health/protected", HttpMethod.GET)
                .withHeader(HttpHeaders.AUTHORIZATION, SecurityParams.TOKEN_PREFIX + token)
                .getResponse()
                .assertStatus(HttpStatus.OK);
    }

    @Test
    public void tokenExpired() {
        String token = JwtUtils.createJwtToken(Instant.now().plusSeconds(-10), "V4CYY1ZzMH6g6tT2GqOtYABiieKuoHci", Collections.singletonMap("id", "1"));

        request("/health/protected", HttpMethod.GET)
                .withHeader(HttpHeaders.AUTHORIZATION, SecurityParams.TOKEN_PREFIX + token)
                .getResponse()
                .assertStatus(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void invalidToken() {
        String token = "foo";

        request("/health/protected", HttpMethod.GET)
                .withHeader(HttpHeaders.AUTHORIZATION, SecurityParams.TOKEN_PREFIX + token)
                .getResponse()
                .assertStatus(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void invalidHeaderName() {
        String token = JwtUtils.createJwtToken(Instant.now().plusSeconds(-10), "V4CYY1ZzMH6g6tT2GqOtYABiieKuoHci", Collections.singletonMap("id", "1"));

        request("/health/protected", HttpMethod.GET)
                .withHeader("foo", SecurityParams.TOKEN_PREFIX + token)
                .getResponse()
                .assertStatus(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void noHeader() {
        request("/health/protected", HttpMethod.GET)
                .getResponse()
                .assertStatus(HttpStatus.UNAUTHORIZED);
    }
}
