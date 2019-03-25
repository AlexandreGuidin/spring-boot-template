package com.springboot.template.controller;

import com.springboot.template.security.dto.LoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Authentication", description = " ")
@RestController
public class LoginFakeController {

    @ApiOperation("Login")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Login success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    @PostMapping("/login")
    public void fakeLogin(@RequestBody LoginRequest request) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }
}
