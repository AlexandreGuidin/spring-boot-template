package com.springboot.template.controller;

import com.springboot.template.Application;
import com.springboot.template.core.BaseControllerTest;
import com.springboot.template.dto.HealthDTO;
import com.springboot.template.exception.ApiException;
import com.springboot.template.service.HealthService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@TestPropertySource(locations = "classpath:application-test.yml")
public class HealthControllerTest extends BaseControllerTest {

    @MockBean
    private HealthService service;

    private HealthDTO mock() {
        return new HealthDTO()
                .setStatus("UP")
                .setTime("2018-01-01T00:00:00.305Z");
    }

    @Test
    public void success() {
        when(service.get()).thenReturn(mock());

        HealthDTO response = request("/health", HttpMethod.GET)
                .getResponse()
                .assertStatus(HttpStatus.OK)
                .getResponse(HealthDTO.class);

        assertThat(response.getStatus()).isEqualTo("UP");
        assertThat(response.getTime()).isEqualTo("2018-01-01T00:00:00.305Z");
    }

    @Test
    public void error() {
        when(service.get()).thenThrow(new ApiException());

        request("/health", HttpMethod.GET)
                .getResponse()
                .assertStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
