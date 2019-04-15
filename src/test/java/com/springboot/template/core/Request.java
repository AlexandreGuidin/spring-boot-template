package com.springboot.template.core;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Request {

    private MockMvc mvc;
    private Gson gson;
    private String path;
    private HttpMethod method;
    private HashMap<String, List<String>> headers = new LinkedHashMap<>();
    private HashMap<String, List<String>> queryParams = new LinkedHashMap<>();
    private String bodyData;

    Request(MockMvc mvc, Gson gson, String path, HttpMethod method) {
        this.mvc = mvc;
        this.gson = gson;
        this.path = path;
        this.method = method;
    }

    public Request withBodyData(Object bodyData) {
        this.bodyData = gson.toJson(bodyData);
        return this;
    }

    public Request withBodyJson(String fileName) {
        try {
            File file = ResourceUtils.getFile("classpath:json/" + fileName);
            String body = new String(Files.readAllBytes(file.toPath()));
            this.bodyData = new JsonParser().parse(body).toString();
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Request withHeader(String name, String value) {
        headers.put(name, Collections.singletonList(value));
        return this;
    }

    public Request withQueryParam(String name, String value) {
        queryParams.put(name, Collections.singletonList(value));
        return this;
    }

    public Response getResponse() {
        try {
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.request(method, path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyData)
                    .headers(new HttpHeaders(new LinkedMultiValueMap<>(headers)))
                    .params(new HttpHeaders(new LinkedMultiValueMap<>(queryParams)));

            MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
            return new Response(gson, mvcResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
