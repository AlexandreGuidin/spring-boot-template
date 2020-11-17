package org.template.core.healthcheck;


import org.template.core.healthcheck.model.HealthStatus;

public interface HealthService {

    HealthStatus execute();
}
