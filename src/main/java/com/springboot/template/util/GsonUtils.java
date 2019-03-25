package com.springboot.template.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.Optional;

public class GsonUtils {

    private GsonUtils() {
    }

    public static String keyFronJson(String json, String key) {
        return Optional.ofNullable(json)
                .map(new JsonParser()::parse)
                .map(JsonElement::getAsJsonObject)
                .map(jsonObject -> jsonObject.get(key))
                .map(JsonElement::getAsString)
                .orElse(null);
    }
}
