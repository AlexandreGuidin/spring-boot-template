package org.template.controller;

import org.template.core.openapi.*;
import org.template.model.request.UserRegisterRequest;
import org.template.model.response.UserResponse;
import org.template.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController("/user")
@Tag(name = "User")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    @Ok
    @Unauthorized
    @UnprocessableEntity
    @InternalServerError
    public void register(@Valid @RequestBody UserRegisterRequest request) {
        service.register(request);
    }

    @GetMapping("/{email}")
    @Ok
    @NotFound
    @Unauthorized
    @UnprocessableEntity
    @InternalServerError
    public UserResponse findByEmail(@NotNull @PathVariable String email) {
        return service.findByEmail(email);
    }
}
