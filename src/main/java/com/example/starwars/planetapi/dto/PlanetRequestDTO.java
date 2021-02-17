package com.example.starwars.planetapi.dto;

import com.example.starwars.planetapi.model.Planet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * Planet Data Transfer Object used in create Planet endpoint
 *
 * @author xiao
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlanetRequestDTO {

	private String name;
	private String climate;
	private String terrain;

	public Planet convertToEntity() {
		return new Planet(name, climate, terrain);
	}
}
