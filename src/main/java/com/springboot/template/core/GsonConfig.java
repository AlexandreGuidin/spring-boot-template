package com.springboot.template.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.springboot.template.core.gson.SwaggerConverter;
import com.springboot.template.core.gson.ZonedDateTimeConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import springfox.documentation.spring.web.json.Json;

import java.time.ZonedDateTime;

@Configuration
@ConditionalOnClass(Gson.class)
public class GsonConfig {

    @Bean
    @ConditionalOnMissingBean
    public CustomGsonBuilderCustomizer customGsonBuilderCustomizer() {
        return new CustomGsonBuilderCustomizer();
    }

    private static final class CustomGsonBuilderCustomizer implements GsonBuilderCustomizer, Ordered {

        @Override
        public void customize(GsonBuilder gsonBuilder) {
            gsonBuilder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeConverter())
                    .registerTypeAdapter(Json.class, new SwaggerConverter());
        }

        @Override
        public int getOrder() {
            return 0;
        }
    }
}
