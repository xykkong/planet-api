package br.rj.b2w.starwars.planetapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.rj.b2w.starwars.planetapi.model.Planet;

/**
 * 
 * Planet Repository
 * 
 * @author xiao
 *
 */
public interface PlanetRepository extends MongoRepository<Planet, String> {

	public List<Planet> findByName(String name);

}
