package com.example.starwars.planetapi.adapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SwapiResponse {
    private int count;
    private List<PlanetSwapi> results;

    public List<String> getFilms() {
        if (results != null && results.get(0) != null) return results.get(0).getFilms();

        return new ArrayList<>();
    }
}
