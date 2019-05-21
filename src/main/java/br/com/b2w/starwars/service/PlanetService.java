package br.com.b2w.starwars.service;

import static br.com.b2w.starwars.config.Constants.endpoint;
import static br.com.b2w.starwars.config.Constants.agent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.b2w.starwars.repository.PlanetRepository;
import br.com.b2w.starwars.metamodel.Planet_;
import br.com.b2w.starwars.model.Planet;

/**
 * Service layer
 * 
 * @author Filipe Oliveira
 *
 */
@Service
public class PlanetService {

	private RestTemplate client = new RestTemplate();

	Logger logger = LoggerFactory.getLogger(PlanetService.class);

	@Autowired
	private PlanetRepository repository;

	public List<Planet> list(String nome) {

		if (StringUtils.isNotBlank(nome)) {
			logger.debug("Listando planetas com nome contendo : " + nome);
			return repository.findByNomeLike(nome);
		} else {
			logger.debug("Listando todos os planetas...");
			return repository.findAll();
		}

	}

	public Planet fetch(String id) {

		Optional<Planet> planet = repository.findById(id);

		if (planet.isPresent()){
			return planet.get();
		} else {
			throw new EmptyResultDataAccessException(1);
		}

	}

	public Planet create(Planet planet) {
		return repository.save(planet);
	}

	public void delete(String id) {
		repository.deleteById(id);
	}

	/**
	 * Consulta dados de planetas através da API {@linkplain https://swapi.co/api/}
	 * Foi simulada a consulta através de números randômicos entre 0 e 60, uma vez
	 * que o ID de planeta difere nas duas API's
	 * 
	 * @param planet Instância de planeta a serem recuperados a quantidade de
	 *               aparições em filmes
	 * @return Retorna uma instância de planeta com o atributo filmes preenchido
	 */
	private Planet fetchFilms(Planet planet) {

		HttpEntity<String> entity = new HttpEntity<String>("parameters", buildHeader());

		ResponseEntity<Planet_> p = client.exchange(endpoint + generateRandom(), HttpMethod.GET, entity, Planet_.class);

		planet.setFilmes(p.getBody().getFilms().size());

		return planet;
	}

	/**
	 * Constrói um Header HTTP padrão com user-agent e accept
	 * 
	 * @return
	 */
	private static HttpHeaders buildHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", agent);
		return headers;
	}

	/**
	 * @return Número randômico entre 0 e 60
	 */
	private static int generateRandom() {
		return ThreadLocalRandom.current().nextInt(0, 60);
	}

}
