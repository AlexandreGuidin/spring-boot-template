package com.springboot.template.core;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;

public class Response {

    private MvcResult result;
    private Gson gson;

    Response(Gson gson, MvcResult result) {
        this.result = result;
        this.gson = gson;
    }

    public Response assertStatus(HttpStatus status) {
        assertThat(result.getResponse().getStatus()).isEqualTo(status.value());
        return this;
    }

    public <T> Response assertEquals(Class<T> cls, Object expected) {
        assertThat(getResponse(cls)).isEqualTo(expected);
        return this;
    }

    public <T> T getResponse(Class<T> cls) {
        try {
            return gson.fromJson(result.getResponse().getContentAsString(), cls);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
