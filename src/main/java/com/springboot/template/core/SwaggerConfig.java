package com.springboot.template.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static io.swagger.models.auth.In.HEADER;
import static java.util.Collections.singletonList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.package}")
    private String swaggerPackage;

    @Value("${swagger.enable}")
    private Boolean enableSwagger;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(singletonList(new ApiKey("JWT", AUTHORIZATION, HEADER.name())))
                .securityContexts(Collections.singletonList(
                        SecurityContext.builder()
                                .securityReferences(
                                        singletonList(SecurityReference.builder()
                                                .reference("JWT")
                                                .scopes(new AuthorizationScope[0])
                                                .build()
                                        )
                                )
                                .build())
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerPackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metadata())
                .enable(enableSwagger);
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("API Documentation")
                .build();
    }
}
