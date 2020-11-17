package org.template.core.healthcheck.model;


import java.util.HashMap;
import java.util.Map;

public class HealthStatus {

    private final String service;
    private final HealthStatusEnum status;
    private Map<String, String> details;

    public HealthStatus(String service, HealthStatusEnum status) {
        this.service = service;
        this.status = status;
    }

    public HealthStatus addDetail(String key, String value) {
        if (details == null) {
            details = new HashMap<>();
        }
        details.put(key, value);
        return this;
    }

    public HealthStatusEnum getStatus() {
        return status;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public String getService() {
        return service;
    }
}
