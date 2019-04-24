package br.rj.b2w.starwars.planetapi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.rj.b2w.starwars.planetapi.model.Planet;
import br.rj.b2w.starwars.planetapi.repository.PlanetRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Class that is executed after container initialization 
 * to fill database with some dummy data
 * 
 * @author xiao
 *
 */
@Component
@Slf4j
public class DbInitializer implements CommandLineRunner {

	private PlanetRepository planetRepository;
	
	@Autowired
	public DbInitializer(PlanetRepository planetRepository) {
		this.planetRepository = planetRepository;
	}

	@Override
	public void run(String... strings) throws Exception {
		planetRepository.deleteAll();

		log.info("Preloading " + planetRepository.save(new Planet("Tatooine", "arid", "desert")));
		log.info("Preloading " + planetRepository.save(new Planet("Alderaan", "temperate", "grasslands, mountains")));
		log.info("Preloading "
				+ planetRepository.save(new Planet("Yavin IV", "temperate, tropical", "jungle, rainforests")));
		log.info("Preloading "
				+ planetRepository.save(new Planet("Hoth", "frozen", "tundra, ice caves, mountain ranges")));
		log.info("Preloading " + planetRepository.save(new Planet("Dagobah", "murky", "swamp, jungles")));
		log.info("Preloading " + planetRepository.save(new Planet("Bespin", "temperate", "gas giant")));
		log.info("Preloading " + planetRepository.save(new Planet("Endor", "temperate", "forests, mountains, lakes")));
		log.info("Preloading "
				+ planetRepository.save(new Planet("Naboo", "temperate", "grassy hills, swamps, forests, mountains")));
		log.info("Preloading " + planetRepository.save(new Planet("Coruscant", "temperate", "cityscape, mountains")));
		log.info("Preloading " + planetRepository.save(new Planet("Kamino", "temperate", "ocean")));

		log.info("--- Database has been initialized");
	}

}