package com.example.starwars.planetapi.utils;

import com.example.starwars.planetapi.model.Planet;
import com.example.starwars.planetapi.repository.PlanetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Class that is executed after container initialization to fill database with some dummy data
 *
 * @author xiao
 */
@Component
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired private PlanetRepository planetRepository;

    @Override
    public void run(String... strings) throws Exception {
        planetRepository.deleteAll();
        log.info("--- Initializing Database with dummy data");
        planetRepository.save(new Planet("Alderaan", "temperate", "grasslands, mountains"));
        planetRepository.save(new Planet("Yavin IV", "temperate, tropical", "jungle, rainforests"));
        planetRepository.save(new Planet("Hoth", "frozen", "tundra, ice caves, mountain ranges"));
        planetRepository.save(new Planet("Dagobah", "murky", "swamp, jungles"));
        planetRepository.save(new Planet("Bespin", "temperate", "gas giant"));
        planetRepository.save(new Planet("Endor", "temperate", "forests, mountains, lakes"));
        planetRepository.save(
                new Planet("Naboo", "temperate", "grassy hills, swamps, forests, mountains"));
        planetRepository.save(new Planet("Coruscant", "temperate", "cityscape, mountains"));
        planetRepository.save(new Planet("Kamino", "temperate", "ocean"));

        log.info("--- Database has been initialized");
    }
}
