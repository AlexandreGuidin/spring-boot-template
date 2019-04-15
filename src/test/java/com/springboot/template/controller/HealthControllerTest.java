package com.springboot.template.controller;

import com.springboot.template.Application;
import com.springboot.template.core.BaseControllerTest;
import com.springboot.template.core.exception.ApiException;
import com.springboot.template.model.HealthModel;
import com.springboot.template.service.HealthService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class HealthControllerTest extends BaseControllerTest {

    @MockBean
    private HealthService service;

    private HealthModel mock() {
        return new HealthModel()
                .setStatus("UP")
                .setTime("2018-01-01T00:00:00.305Z");
    }

    @Test
    public void get() {
        when(service.get()).thenReturn(mock());

        request("/health", HttpMethod.GET)
                .getResponse()
                .assertStatus(HttpStatus.OK)
                .assertJson("json/HealthSuccess.json");
    }

    @Test
    public void error() {
        when(service.get()).thenThrow(new ApiException());

        request("/health", HttpMethod.GET)
                .getResponse()
                .assertStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @WithMockUser
    @Test
    public void getProtected() {
        when(service.get()).thenReturn(mock());

        request("/health/protected", HttpMethod.GET)
                .getResponse()
                .assertStatus(HttpStatus.OK)
                .assertJson("json/HealthSuccess.json");
    }

    @Test
    public void getProtectedWithoutAuthentication() {
        request("/health/protected", HttpMethod.GET)
                .getResponse()
                .assertStatus(HttpStatus.UNAUTHORIZED);
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    public void rolesProtected() {
        when(service.get()).thenReturn(mock());

        request("/health/protected/roles", HttpMethod.GET)
                .getResponse()
                .assertStatus(HttpStatus.OK)
                .assertJson("json/HealthSuccess.json");
    }

    @WithMockUser(roles = {"WRONG_ROLE"})
    @Test
    public void rolesProtectedWithNoRole() {
        request("/health/protected/roles", HttpMethod.GET)
                .getResponse()
                .assertStatus(HttpStatus.FORBIDDEN);
    }
}
