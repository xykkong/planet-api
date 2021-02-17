package com.example.starwars.planetapi.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.starwars.planetapi.model.Planet;

/**
 *
 * Unit test for PlanetResquestDTO
 *
 * @author xiao
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PlanetRequestDTO.class)
public class PlanetRequestDTOTest {

	@Test
	public void convertToEntity_shouldReturnEquals() throws Exception {
		var planetDTO = new PlanetRequestDTO("Alderaan", "temperate", "grasslands");
		var planet = new Planet("Alderaan", "temperate", "grasslands");

		assertEquals(planetDTO.convertToEntity(), planet);
	}
}