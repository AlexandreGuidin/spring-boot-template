package com.springboot.template.core;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

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

    public Response assertHeader(String name, String value) {
        assertThat(result.getResponse().getHeader(name)).isEqualTo(value);
        return this;
    }

    public Response assertHeaderContains(String name, String value) {
        assertThat(result.getResponse().getHeader(name)).contains(value);
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

    public <T> List<T> getResponseList(Class<T[]> cls) {
        try {
            T[] array = gson.fromJson(result.getResponse().getContentAsString(), cls);
            return Arrays.asList(array);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response assertJson(String fileName) {
        try {
            File file = ResourceUtils.getFile("classpath:json/" + fileName);
            String json = new String(Files.readAllBytes(file.toPath()));
            String content = json.replaceAll("\\s+(?=((\\\\[\\\\\"]|[^\\\\\"])*\"(\\\\[\\\\\"]|[^\\\\\"])*\")*(\\\\[\\\\\"]|[^\\\\\"])*$)", "");
            assertThat(result.getResponse().getContentAsString()).isEqualTo(content);
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
