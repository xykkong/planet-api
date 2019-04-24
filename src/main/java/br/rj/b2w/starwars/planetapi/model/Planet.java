package br.rj.b2w.starwars.planetapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Planet Entity
 * 
 * @author xiao
 *
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Planet {

	private @Id @GeneratedValue String id;
	private String name;
	private String climate;
	private String terrain;
	private String starWarsApparition;

	public Planet(String name, String climate, String terrain) {
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
	}

	public void setStarWarsApparition(int starWarsApparition) {
		this.starWarsApparition = String.valueOf(starWarsApparition);
	}

}