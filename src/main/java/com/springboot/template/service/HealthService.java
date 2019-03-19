package com.springboot.template.service;

import com.springboot.template.dto.HealthDTO;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class HealthService {

    public HealthDTO get() {
        return new HealthDTO()
                .setStatus("UP")
                .setTime(ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE));
    }
}
