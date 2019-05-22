package br.com.b2w.starwars.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.b2w.starwars.model.Planet;
/**
 * Repository layer
 * 
 * @author Filipe Oliveira
 *
 */
@Repository
public interface PlanetRepository extends MongoRepository<Planet, String>{

	List<Planet> findByNomeLike(@Param("nome") String nome);

	Planet findByNome(@Param("nome") String nome);
	
}
