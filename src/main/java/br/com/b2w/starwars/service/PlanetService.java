package br.com.b2w.starwars.service;

import static br.com.b2w.starwars.config.Constants.endpoint;
import static br.com.b2w.starwars.config.Constants.agent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
	private PlanetRepository repository;
	
	public List<Planet> list(){
		return repository.findAll();		
	}
	
	public Planet fetch(String id) {
		return this.fetchFilms(repository.findOne(id));		
	}
	
	public Planet fetchByName(String nome) {
		return this.fetchFilms(repository.findByNome(nome));
	}
	
	public Planet createPlanet(Planet planet) {
		return repository.save(planet);
	}
	
	public void delete(String id) {
		repository.delete(id);
	}
	/**
	 * Consulta dados de planetas através da API {@linkplain https://swapi.co/api/}
	 * Foi simulada a consulta através de números randômicos entre 0 e 60, 
	 * uma vez que o ID de planeta difere nas duas API's
	 *  
	 * @param planet Instância de planeta a serem recuperados a quantidade de aparições em filmes
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
