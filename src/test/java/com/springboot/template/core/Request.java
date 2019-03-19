package com.springboot.template.core;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Arrays;
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
    private Object bodyData;

    Request(MockMvc mvc, Gson gson, String path, HttpMethod method) {
        this.mvc = mvc;
        this.gson = gson;
        this.path = path;
        this.method = method;
    }

    public Request withBodyData(Object bodyData) {
        this.bodyData = bodyData;
        return this;
    }

    public Request withHeader(String name, String value) {
        headers.put(name, Arrays.asList(value));
        return this;
    }

    public Request withQueryParam(String name, String value) {
        queryParams.put(name, Arrays.asList(value));
        return this;
    }

    public Response getResponse() {
        try {
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.request(method, path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(bodyData))
                    .headers(new HttpHeaders(new LinkedMultiValueMap<>(headers)))
                    .params(new HttpHeaders(new LinkedMultiValueMap<>(queryParams)));

            MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
            return new Response(gson, mvcResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
