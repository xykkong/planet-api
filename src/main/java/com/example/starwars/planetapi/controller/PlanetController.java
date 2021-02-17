package com.example.starwars.planetapi.controller;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.starwars.planetapi.dto.PlanetRequestDTO;
import com.example.starwars.planetapi.model.Planet;
import com.example.starwars.planetapi.repository.PlanetRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 *
 * Rest API Controller for Planets endpoint
 *
 * @author xiao
 *
 */
@RestController
@RequestMapping("/api/v1/planets")
@Api(value = "PlanetAPI", tags = {
		"Planet API" }, description = "API that can be used to list, create and delete a Planet")
public class PlanetController {

	private PlanetRepository planetRepository;

	private static final String swapiURL = "https://swapi.dev/api/planets/?search=";

	@Autowired
	public PlanetController(PlanetRepository planetRepository) {
		this.planetRepository = planetRepository;
	}

	/*
	 * TODO Maybe pagination is necessary here
	 */
	@ApiOperation(value = "Get a list of Planets", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Planets retrieved successfully") })
	@GetMapping
	public List<Planet> getPlanets(@ApiParam(value = "Planet name") @RequestParam(required = false) String name) {

		List<Planet> planets = (name == null) ? planetRepository.findAll() : planetRepository.findByName(name);

		if (planets == null)
			planets = new ArrayList<Planet>();

		planets.forEach(planet -> {
			int apparition = findStarWarsApparition(planet.getName());
			planet.setStarWarsApparition(apparition);
		});

		return planets;
	}

	@ApiOperation(value = "Get a Planet by ID", response = Planet.class, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Planet found successfully"),
			@ApiResponse(code = 404, message = "Planet not found") })
	@GetMapping("/{id}")
	public ResponseEntity<Object> getPlanetById(
			@ApiParam(value = "Planet ID", required = true) @PathVariable String id) {

		Optional<Planet> planetOptional = planetRepository.findById(id);

		if (!planetOptional.isPresent())
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().body(planetOptional.get());
	}

	@ApiOperation(value = "Create a new Planet", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Planet created successfully"),
			@ApiResponse(code = 400, message = "Required request body is missing") })
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Object> createPlanet(
			@ApiParam(value = "Planet", name = "Planet", required = true) @Valid @RequestBody PlanetRequestDTO planetDTO) {
		Planet planet = planetRepository.save(planetDTO.convertToEntity());

		URI locationUri = UriComponentsBuilder.fromPath("/planets/" + planet.getId()).build().toUri();

		return ResponseEntity.created(locationUri).build();
	}

	@ApiOperation(value = "Delete a Planet by ID", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Planet deleted successfully"),
			@ApiResponse(code = 404, message = "Planet Not Found") })
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<Object> deletePlanet(@ApiParam(value = "Planet ID") @PathVariable String id) {

		if (!planetRepository.findById(id).isPresent())
			return ResponseEntity.notFound().build();

		planetRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Connect to SWAPI API to find a number of Star Wars apparitions of
	 * a planet
	 */
	private int findStarWarsApparition(String planetName) {
		try {
			RestTemplate restTemplate = new RestTemplateBuilder().setConnectTimeout(Duration.ofMinutes(60))
					.setReadTimeout(Duration.ofSeconds(30)).build();
			ResponseEntity<String> response = restTemplate.getForEntity(swapiURL + planetName, String.class);

			JsonNode root = new ObjectMapper().readTree(response.getBody());
			int count = root.get("count").asInt();
			JsonNode results = root.get("results");
			if (count >= 1) {
				JsonNode films = results.get(0).get("films");
				if (films != null && films.isArray()) {
					return films.size();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}
