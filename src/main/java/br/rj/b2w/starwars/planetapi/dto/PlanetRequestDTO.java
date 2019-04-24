package br.rj.b2w.starwars.planetapi.dto;

import br.rj.b2w.starwars.planetapi.model.Planet;
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