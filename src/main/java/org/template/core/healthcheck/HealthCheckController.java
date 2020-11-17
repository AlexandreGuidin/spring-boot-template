package org.template.core.healthcheck;



import org.template.core.healthcheck.model.HealthStatus;
import org.template.core.healthcheck.model.HealthStatusEnum;
import org.template.core.openapi.InternalServerError;
import org.template.core.openapi.Ok;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Health Check")
@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    private final List<HealthService> healthChecks;

    @Autowired
    public HealthCheckController(List<HealthService> healthChecks) {
        this.healthChecks = healthChecks;
    }

    @Ok
    @InternalServerError
    @ApiResponse(responseCode = "503", description = "Unavailable")
    @GetMapping("/readiness")
    public ResponseEntity<List<HealthStatus>> readiness() {
        List<HealthStatus> allStatus = healthChecks.stream()
                .map(HealthService::execute)
                .collect(Collectors.toList());

        if (allStatus.stream().anyMatch(s -> s.getStatus().equals(HealthStatusEnum.UNAVAILABLE))) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(allStatus);
        }

        return ResponseEntity.status(HttpStatus.OK).body(allStatus);
    }

    @Ok
    @InternalServerError
    @GetMapping("/liveness")
    public HealthStatus liveness() {
        return new HealthStatus("liveness", HealthStatusEnum.OK);
    }
}
