package com.example.starwars.planetapi.adapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetSwapi {
    private List<String> films;
}
