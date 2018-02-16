package br.com.b2w.starwars.rest;

import static br.com.b2w.starwars.config.Constants.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.b2w.starwars.model.Planet;
import br.com.b2w.starwars.service.PlanetService;
/**
 * Recurso responsável pelo processamento das requisições ao path /planets
 * @author Filipe Oliveira
 *
 */
@RestController
@RequestMapping(value="/planets", produces=JSON)
public class PlanetResource {
	
	// TODO Remove
	@Autowired
	Planet planet;
	
	@Autowired
	PlanetService service;
	
	@GetMapping
	public ResponseEntity<Planet> list(){
		return new ResponseEntity<Planet>(planet, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Planet> fetchById(@PathVariable("id") int id){
		return new ResponseEntity<Planet>(service.fetchById(Long.valueOf(id)), HttpStatus.FOUND);
	}
	/*
	@GetMapping(value="/{name}")
	public ResponseEntity<Planet> fetchByName(@PathVariable("name") String name){
		return new ResponseEntity<Planet>(service.fetchByName(name), HttpStatus.FOUND);
	}
	*/
	@PutMapping
	public ResponseEntity<Void> create(@RequestBody Planet planet, UriComponentsBuilder ucBuilder){
		// TODO service.createPlanet(planet)
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> remove(@PathVariable("id") int id){
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
