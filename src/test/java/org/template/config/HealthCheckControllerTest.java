package org.template.config;



import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.template.Application;
import org.template.core.exception.ApiException;
import org.template.core.healthcheck.DatabaseHealthService;
import org.template.core.healthcheck.model.HealthStatus;
import org.template.core.healthcheck.model.HealthStatusEnum;
import org.test.BaseControllerTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class HealthCheckControllerTest extends BaseControllerTest {

    @MockBean
    private DatabaseHealthService databaseHealthService;

    @Test
    public void readiness_all_ok() throws Exception {
        when(databaseHealthService.execute()).thenReturn(new HealthStatus("database", HealthStatusEnum.OK));

        mvc.perform(get("/health-check/readiness")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(readJson("json/healthcheck/readinessOk.json")));
    }

    @Test
    public void readiness_all_unavailable() throws Exception {
        when(databaseHealthService.execute()).thenReturn(new HealthStatus("database", HealthStatusEnum.UNAVAILABLE));

        mvc.perform(get("/health-check/readiness")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isServiceUnavailable())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(readJson("json/healthcheck/readinessUnavailable.json")));
    }

    @Test
    public void readiness_exception() throws Exception {
        when(databaseHealthService.execute()).thenThrow(new ApiException());

        mvc.perform(get("/health-check/readiness")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }


    @Test
    public void liveness_ok() throws Exception {
        mvc.perform(get("/health-check/liveness")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(readJson("json/healthcheck/livenessOk.json")));
    }
}
