package com.springboot.template.service;

import com.springboot.template.model.HealthModel;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class HealthService {

    public HealthModel get() {
        return new HealthModel()
                .setStatus("UP")
                .setTime(ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
