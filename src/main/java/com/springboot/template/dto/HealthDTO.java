package com.springboot.template.dto;

public class HealthDTO {
    private String status;
    private String time;

    public String getStatus() {
        return status;
    }

    public HealthDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getTime() {
        return time;
    }

    public HealthDTO setTime(String time) {
        this.time = time;
        return this;
    }
}
