package com.springboot.template.controller;

import com.springboot.template.dto.HealthDTO;
import com.springboot.template.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final HealthService service;

    @Autowired
    public HealthController(HealthService service) {
        this.service = service;
    }

    @GetMapping
    private HealthDTO get() {
        return service.get();
    }

    @RequestMapping("/protected")
    @GetMapping
    private HealthDTO getProtected() {
        return service.get();
    }

    @RequestMapping("/protected/roles")
    @GetMapping
    private HealthDTO rolesProtected() {
        return service.get();
    }
}
