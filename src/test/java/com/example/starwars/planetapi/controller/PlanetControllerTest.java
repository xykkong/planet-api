package com.example.starwars.planetapi.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.starwars.planetapi.dto.PlanetRequestDTO;
import com.example.starwars.planetapi.model.Planet;
import com.example.starwars.planetapi.repository.PlanetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Unit test for PlanetController
 *
 * @author xiao
 */
@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private PlanetRepository planetRepository;

    @Autowired private ObjectMapper mapper;

    private static final String planetEndpoint = "/api/v1/planets";

    /** Testing getAllPlanets resource */
    @Test
    public void getAllPlanets_shouldReturnOk() throws Exception {
        List<Planet> planets =
                Arrays.asList(
                        new Planet("1", "Tatooine", "arid", "desert", "5"),
                        new Planet("2", "Alderaan", "temperate", "grasslands, mountains", "2"),
                        new Planet(
                                "3",
                                "Yavin IV",
                                "temperate, tropical",
                                "jungle, rainforests",
                                "1"));

        when(planetRepository.findAll()).thenReturn(planets);

        mockMvc.perform(get(planetEndpoint))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("Tatooine")))
                .andExpect(jsonPath("$[0].climate", is("arid")))
                .andExpect(jsonPath("$[0].terrain", is("desert")))
                .andExpect(jsonPath("$[0].starWarsApparition", is("5")))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].name", is("Alderaan")))
                .andExpect(jsonPath("$[1].climate", is("temperate")))
                .andExpect(jsonPath("$[1].terrain", is("grasslands, mountains")))
                .andExpect(jsonPath("$[1].starWarsApparition", is("2")))
                .andExpect(jsonPath("$[2].id", is("3")))
                .andExpect(jsonPath("$[2].name", is("Yavin IV")))
                .andExpect(jsonPath("$[2].climate", is("temperate, tropical")))
                .andExpect(jsonPath("$[2].terrain", is("jungle, rainforests")))
                .andExpect(jsonPath("$[2].starWarsApparition", is("1")));

        verify(planetRepository, times(1)).findAll();
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    public void getAllPlanets_EmptyList_shouldReturnOk() throws Exception {

        when(planetRepository.findAll()).thenReturn(new ArrayList<Planet>());

        mockMvc.perform(get(planetEndpoint))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(planetRepository, times(1)).findAll();
        verifyNoMoreInteractions(planetRepository);
    }

    /** Testing getAllPlanetByName resource */
    @Test
    public void getPlanetByName_Alderaan_shouldReturnOk() throws Exception {
        List<Planet> planets =
                Arrays.asList(
                        new Planet("1", "Alderaan", "temperate", "grasslands, mountains", ""),
                        new Planet("2", "Alderaan", "temperate", "grasslands, mountains", ""));

        when(planetRepository.findByName("Alderaan")).thenReturn(planets);

        mockMvc.perform(get(planetEndpoint + "?name=Alderaan"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("Alderaan")))
                .andExpect(jsonPath("$[0].climate", is("temperate")))
                .andExpect(jsonPath("$[0].terrain", is("grasslands, mountains")))
                .andExpect(jsonPath("$[0].starWarsApparition", is("2")))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].name", is("Alderaan")))
                .andExpect(jsonPath("$[1].climate", is("temperate")))
                .andExpect(jsonPath("$[1].terrain", is("grasslands, mountains")))
                .andExpect(jsonPath("$[1].starWarsApparition", is("2")));

        verify(planetRepository, times(1)).findByName("Alderaan");
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    public void getPlanetByName_Mars_shouldReturnNotFound() throws Exception {

        when(planetRepository.findByName("mars")).thenReturn(null);

        mockMvc.perform(get(planetEndpoint + "?name=mars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(planetRepository, times(1)).findByName("mars");
        verifyNoMoreInteractions(planetRepository);
    }

    /** Testing getPlanetById resource */
    @Test
    public void getPlanetById_1_shouldReturnOk() throws Exception {
        Optional<Planet> planet =
                Optional.of(new Planet("1", "Alderaan", "temperate", "grasslands, mountains", "2"));

        when(planetRepository.findById("1")).thenReturn(planet);

        mockMvc.perform(get(planetEndpoint + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("Alderaan")))
                .andExpect(jsonPath("$.climate", is("temperate")))
                .andExpect(jsonPath("$.terrain", is("grasslands, mountains")))
                .andExpect(jsonPath("$.starWarsApparition", is("2")));

        verify(planetRepository, times(1)).findById("1");
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    public void getPlanetById_null_shouldReturnNotFound() throws Exception {

        when(planetRepository.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(get(planetEndpoint + "/1")).andExpect(status().isNotFound());

        verify(planetRepository, times(1)).findById("1");
        verifyNoMoreInteractions(planetRepository);
    }

    /** Testing create planet resource */
    @Test
    public void createPlanet_Tatooine_shouldReturnCreated() throws Exception {

        var planetDTO = new PlanetRequestDTO("Tatooine", "arid", "desert");

        var planetPersisted = planetDTO.convertToEntity();
        planetPersisted.setId("123");

        when(planetRepository.save(planetDTO.convertToEntity())).thenReturn(planetPersisted);

        mockMvc.perform(
                        post(planetEndpoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(planetDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/planets/" + planetPersisted.getId()));

        verify(planetRepository, times(1)).save(planetDTO.convertToEntity());
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    public void createPlanet_null_shouldReturnBadRequest() throws Exception {

        mockMvc.perform(post(planetEndpoint)).andExpect(status().isBadRequest());
    }

    /** Testing delete planet resource */
    @Test
    public void deletePlanet_1_shouldReturnOk() throws Exception {
        Optional<Planet> planet =
                Optional.of(
                        new Planet(
                                "1", "Yavin IV", "temperate, tropical", "jungle, rainforests", ""));

        var idPlanet = planet.get().getId();

        when(planetRepository.findById(idPlanet)).thenReturn(planet);
        doNothing().when(planetRepository).deleteById(idPlanet);

        mockMvc.perform(
                        delete(planetEndpoint + "/" + idPlanet)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(planetRepository, times(1)).findById(idPlanet);
        verify(planetRepository, times(1)).deleteById(idPlanet);
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    public void deletePlanet_null_shouldReturnNotFound() throws Exception {
        String idPlanet = "1";

        when(planetRepository.findById(idPlanet)).thenReturn(Optional.empty());
        doNothing().when(planetRepository).deleteById(idPlanet);

        mockMvc.perform(delete(planetEndpoint + "/" + idPlanet)).andExpect(status().isNotFound());

        verify(planetRepository, times(1)).findById(idPlanet);
        verify(planetRepository, times(0)).deleteById(idPlanet);

        verifyNoMoreInteractions(planetRepository);
    }
}
