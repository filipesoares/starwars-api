package br.com.b2w.starwars.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.b2w.starwars.model.Planet;

@Repository
public interface PlanetRepository extends MongoRepository<Planet, String>{

	Planet findByNome(@Param("nome") String nome);
	
}
