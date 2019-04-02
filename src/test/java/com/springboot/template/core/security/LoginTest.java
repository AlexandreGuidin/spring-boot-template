package com.springboot.template.core.security;

import com.springboot.template.Application;
import com.springboot.template.core.BaseControllerTest;
import com.springboot.template.core.security.dto.LoginRequest;
import com.springboot.template.mock.LoginMock;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class LoginTest extends BaseControllerTest {

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void login() {
        when(authenticationService.loadUserByUsername(any())).thenReturn(LoginMock.mockAuthenticated());

        LoginRequest login = new LoginRequest("test@test.com", "123");

        request("/login", HttpMethod.POST)
                .withBodyData(login)
                .getResponse()
                .assertStatus(HttpStatus.OK)
                .assertHeaderContains(HttpHeaders.AUTHORIZATION, SecurityParams.TOKEN_PREFIX)
                .assertJson("login.json");
    }

    @Test
    public void loginWrongPassword() {
        when(authenticationService.loadUserByUsername(any())).thenReturn(LoginMock.mockAuthenticated());

        LoginRequest login = new LoginRequest("test@test.com", "111");

        request("/login", HttpMethod.POST)
                .withBodyData(login)
                .getResponse()
                .assertStatus(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void loginEmailNotFound() {
        when(authenticationService.loadUserByUsername(any())).thenThrow(new UsernameNotFoundException(""));

        LoginRequest login = new LoginRequest("test@test.com", "111");

        request("/login", HttpMethod.POST)
                .withBodyData(login)
                .getResponse()
                .assertStatus(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void loginRequestInvalid() {
        when(authenticationService.loadUserByUsername(any())).thenThrow(new UsernameNotFoundException(""));

        LoginRequest login = new LoginRequest(null, "123");

        request("/login", HttpMethod.POST)
                .withBodyData(login)
                .getResponse()
                .assertStatus(HttpStatus.UNAUTHORIZED);

        login = new LoginRequest("test@test.com", null);

        request("/login", HttpMethod.POST)
                .withBodyData(login)
                .getResponse()
                .assertStatus(HttpStatus.UNAUTHORIZED);

        login = new LoginRequest();

        request("/login", HttpMethod.POST)
                .withBodyData(login)
                .getResponse()
                .assertStatus(HttpStatus.UNAUTHORIZED);
    }
}
