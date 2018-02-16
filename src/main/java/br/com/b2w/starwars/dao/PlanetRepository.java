package br.com.b2w.starwars.dao;

import org.springframework.stereotype.Repository;

import br.com.b2w.starwars.model.Planet;

@Repository
public class PlanetRepository {
	
	public Planet findById(Long id) {
		return new Planet(Long.valueOf(2), "Alderaan", "Temperado", "Montanhoso"); 
	}

}
