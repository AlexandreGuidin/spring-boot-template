package com.springboot.template.model;

public class HealthModel {
    private String status;
    private String time;

    public String getStatus() {
        return status;
    }

    public HealthModel setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getTime() {
        return time;
    }

    public HealthModel setTime(String time) {
        this.time = time;
        return this;
    }
}
