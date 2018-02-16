package br.com.b2w.starwars.service;

import static br.com.b2w.starwars.config.Constants.endpoint;
import static br.com.b2w.starwars.config.Constants.agent;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.b2w.starwars.dao.PlanetRepository;
import br.com.b2w.starwars.metamodel.Planet_;
import br.com.b2w.starwars.model.Planet;
/**
 * Camada de Serviço que atende as requisições ao recurso de planetas e a outros serviços da aplicação
 * 
 * @author Filipe Oliveira
 *
 */
@Service
public class PlanetService {
	
	private RestTemplate client = new RestTemplate();
	
	@Autowired
	private Planet planet;
	
	@Autowired
	private PlanetRepository repository;
	
	public List<Planet> list(){
		// TODO repository.findAll();
		return null;
	}
	
	public Planet fetchById(Long id) {
		this.planet = repository.findById(id);
		this.fetchFilms();
		return this.planet;
	}
	
	public Planet fetchByName(String name) {
		// TODO repository.findByName(name.trim());
		return null;
	}
	
	public void createPlanet(Planet planet) {
		// TODO repository.save(planet);
	}
	
	public void delete() {
		// TODO repository.remove(planet);
	}
	
	// Methods of external API
	
	private void fetchFilms() {
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", buildHeader());
		
		ResponseEntity<Planet_> p = client.exchange(endpoint + this.planet.getId(), HttpMethod.GET, entity, Planet_.class);
		
		this.planet.setFilmes(p.getBody().getFilms().size());
	}
	
	private static HttpHeaders buildHeader() {	
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", agent);
		return headers;		
	}
	
}
