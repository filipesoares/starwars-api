package br.com.b2w.starwars.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.b2w.starwars.model.Planet;
import br.com.b2w.starwars.repository.PlanetRepository;

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

	public static final String ENDPOINT = "https://swapi.co/api/planets/";
	public static final String AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.167 Safari/537.36";

	@Autowired
	private PlanetRepository repository;

	public List<Planet> list(String nome) {

		if (StringUtils.isNotBlank(nome)) {
			logger.info("Listando planetas com nome contendo : " + nome);
			return repository.findByNomeLike(nome);
		} else {
			logger.info("Listando todos os planetas...");
			return repository.findAll();
		}

	}

	public Planet fetch(String id) throws IOException {

		logger.debug("Consulta de planeta com id %s", id);

		Optional<Planet> planet = repository.findById(id);

		if (planet.isPresent()) {
			return loadFilms(planet.get());
		} else {
			logger.debug("Planeta %s não encontrado", id);
			throw new EmptyResultDataAccessException(1);
		}

	}

	public Planet create(Planet planet) {
		return repository.save(planet);
	}

	public void delete(String id) {
		logger.debug("Exclusão do planeta %s em andamento", id);
		repository.deleteById(id);
	}

	private Planet loadFilms(Planet planet) throws IOException {

		logger.info("Recuperando filmes do planeta %s", planet.getNome());
		HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", buildHeader());
		ResponseEntity<String> response = client.exchange(ENDPOINT + "?search=" + planet.getNome(), HttpMethod.GET, requestEntity, String.class);
		
		if (response.getStatusCode().equals(HttpStatus.OK)){
			
			ObjectMapper mapper = new ObjectMapper();
			
			JsonNode body = mapper.readTree(response.getBody());
			logger.debug(body.toString());

			JsonNode count = body.path("count");
			logger.debug("Founded " + count.intValue() + " planets");

			body.withArray("results").forEach( planetNode -> {
				logger.debug( "Encontrados " + planetNode.withArray("films").size() + " filmes para o planeta " + planetNode.path("name").toString());
				planet.setFilmes(planetNode.withArray("films").size());
			});

		}
		
		return planet;
	}

	private static HttpHeaders buildHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", AGENT);
		return headers;
	}

}
