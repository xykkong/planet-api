package com.example.starwars.planetapi.repository;

import com.example.starwars.planetapi.model.Planet;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Planet Repository
 *
 * @author xiao
 */
public interface PlanetRepository extends MongoRepository<Planet, String> {

    public List<Planet> findByName(String name);
}
