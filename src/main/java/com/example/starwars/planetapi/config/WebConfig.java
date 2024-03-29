package com.example.starwars.planetapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * General Web Configuration
 *
 * @author xiao
 */
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    /*
     * Redirect to Swagger documentation page
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.info("--- Adding Redirect Servlet");
        registry.addRedirectViewController("/", "swagger-ui/index.html");
    }

    /*
     * CORS filter configuration
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("--- Adding Global CORS Configuration");
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "DELETE")
                .maxAge(3600);
    }
}
