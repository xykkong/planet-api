package com.example.starwars.planetapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger Configuration
 *
 * @author xiao
 */
@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfig {

    private static final String resourcePackage = "com.example.starwars.planetapi.controller";

    @Bean
    public Docket api() {
        log.info("--- Initializing Swagger");
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage(resourcePackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(addApiInfo());
    }

    private ApiInfo addApiInfo() {
        return new ApiInfoBuilder()
                .title("Planet REST API")
                .description("\"A simple REST API built using Spring Boot\"")
                .version("v1")
                .build();
    }
}
