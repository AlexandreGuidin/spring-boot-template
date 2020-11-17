package org.template.controller;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.template.Application;
import org.test.BaseControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class UserControllerTest extends BaseControllerTest {

    @Test
    public void register_user_success() throws Exception {
        mvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("json/user/register_success.json")))
                .andExpect(status().isCreated());
    }

    @Test
    public void register_user_validation_error() throws Exception {
        mvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("json/user/register_validation_error.json")))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(readJson("json/user/register_validation_error_response.json")));
    }

    @Test
    public void find_by_email_not_found() throws Exception {
        mvc.perform(get("/user/foo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void find_by_email_success() throws Exception {
        mvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("json/user/register_success.json")))
                .andExpect(status().isCreated());

        mvc.perform(get("/user/mail@mail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(readJson("json/user/find_by_email_success.json")));
    }
}
