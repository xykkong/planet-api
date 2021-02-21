package com.example.starwars.planetapi.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class SwapiAdapter {

    @Getter private SwapiResponse response;
    private static final String swapiURL = "https://swapi.dev/api/planets/?search=";

    public SwapiAdapter(String planetName) {
        RestTemplate restTemplate =
                new RestTemplateBuilder()
                        .setConnectTimeout(Duration.ofMinutes(60))
                        .setReadTimeout(Duration.ofSeconds(30))
                        .build();
        ResponseEntity<String> res = restTemplate.getForEntity(swapiURL + planetName, String.class);
        SwapiResponse swapiApi = null;
        try {
            swapiApi = new ObjectMapper().readValue(res.getBody(), SwapiResponse.class);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        this.response = swapiApi;
    }
}
