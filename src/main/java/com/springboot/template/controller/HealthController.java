package com.springboot.template.controller;

import com.springboot.template.model.HealthModel;
import com.springboot.template.service.HealthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Health Check", description = " ")
@RestController("/health")
public class HealthController {

    private final HealthService service;

    public HealthController(HealthService service) {
        this.service = service;
    }

    @ApiOperation("Check")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    @GetMapping
    private HealthModel get() {
        return service.get();
    }

    @ApiOperation("Check with authentication")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    @GetMapping("/protected")
    private HealthModel getProtected() {
        return service.get();
    }


    @ApiOperation("Check with authentication role")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    @GetMapping("/protected/roles")
    private HealthModel rolesProtected() {
        return service.get();
    }
}
