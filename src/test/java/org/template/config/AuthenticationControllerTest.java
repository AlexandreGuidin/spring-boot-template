package org.template.config;



import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.template.Application;
import org.test.BaseControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class AuthenticationControllerTest extends BaseControllerTest {

    @Test
    public void authenticate() throws Exception {
        mvc.perform(post("/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("json/authentication/valid_user.json")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void authenticate_failed() throws Exception {
        mvc.perform(post("/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("json/authentication/invalid_user.json")))
                .andExpect(status().isUnauthorized());
    }
}
