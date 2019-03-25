package com.springboot.template.controller;

import com.springboot.template.security.dto.LoginRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@ApiOperation(value = "Login")
@RestController
public class LoginFakeController {

    @ApiOperation("Login")
    @PostMapping("/login")
    public void fakeLogin(@RequestBody LoginRequest request) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }
}
