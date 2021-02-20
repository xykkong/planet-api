package com.example.starwars.planetapi.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.starwars.planetapi.model.Planet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

/**
 * Unit test for PlanetResquestDTO
 *
 * @author xiao
 */
@WebMvcTest(PlanetRequestDTO.class)
public class PlanetRequestDTOTest {

    @Test
    public void convertToEntity_shouldReturnEquals() throws Exception {
        var planetDTO = new PlanetRequestDTO("Alderaan", "temperate", "grasslands");
        var planet = new Planet("Alderaan", "temperate", "grasslands");

        assertEquals(planetDTO.convertToEntity(), planet);
    }
}
