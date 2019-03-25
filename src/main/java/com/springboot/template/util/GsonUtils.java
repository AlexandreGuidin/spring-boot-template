package com.springboot.template.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class GsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

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

    public static <T> T castJson(String json, Class<T> cls) {
        try {
            return new Gson().fromJson(json, cls);
        } catch (Exception ex) {
            logger.error("GsonUtils.castJson error", ex);
            return null;
        }
    }
}
