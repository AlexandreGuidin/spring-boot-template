package com.springboot.template.core.gson;

import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeConverter implements JsonSerializer<ZonedDateTime>, JsonDeserializer<ZonedDateTime> {

    public ZonedDateTimeConverter() {
    }

    public JsonElement serialize(ZonedDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return serialize(src);
    }

    public JsonElement serialize(ZonedDateTime src) {
        if (src == null) {
            return null;
        } else {
            String format = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(src.withZoneSameInstant(ZoneId.of("UTC")));
            return new Gson().toJsonTree(format);
        }
    }

    public ZonedDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json != null ? deserialize(json.getAsString()) : null;
    }

    public ZonedDateTime deserialize(String json) {
        return StringUtils.isNotBlank(json) ? ZonedDateTime.parse(json, DateTimeFormatter.ISO_OFFSET_DATE_TIME) : null;
    }
}
