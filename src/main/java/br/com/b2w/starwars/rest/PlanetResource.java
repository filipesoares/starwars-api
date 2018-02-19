package br.com.b2w.starwars.rest;

import static br.com.b2w.starwars.config.Constants.JSON;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.b2w.starwars.model.Planet;
import br.com.b2w.starwars.service.PlanetService;
import io.swagger.annotations.ApiOperation;
/**
 * Recurso responsável pelo processamento das requisições ao path /planets
 * 
 * @author Filipe Oliveira
 *
 */
@RestController
@RequestMapping(value="/planets", produces=JSON)
public class PlanetResource {
	
	@Autowired
	PlanetService service;
	
	@GetMapping
	@ApiOperation(value="Listar Planetas", httpMethod="GET", response=Planet.class, responseContainer="List", code=200)
	public ResponseEntity<List<Planet>> list(){
		return new ResponseEntity<List<Planet>>(service.list(), HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	@ApiOperation(value="Consultar por ID", httpMethod="GET", response=Planet.class, code=302)
	public ResponseEntity<Planet> fetchById(@PathVariable("id") String id){
		try {
			return new ResponseEntity<Planet>(service.fetch(id), HttpStatus.FOUND);
		} catch (NullPointerException e) {
			return new ResponseEntity<Planet>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<Planet>(HttpStatus.BAD_GATEWAY);
		}
	}
	
	@GetMapping(value="/search")
	@ApiOperation(value="Consultar por Nome", httpMethod="GET", response=Planet.class, code=302)
	public ResponseEntity<Planet> fetchByName(@RequestParam(name="nome", required=false) String nome){
		try {
			return new ResponseEntity<Planet>(service.fetchByName(nome), HttpStatus.FOUND);
		} catch (NullPointerException e) {
			return new ResponseEntity<Planet>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<Planet>(HttpStatus.BAD_GATEWAY);
		}
	}
	
	@PutMapping
	@ApiOperation(value="Criar Novo Planeta", httpMethod="PUT", response=Planet.class, code=201)
	public ResponseEntity<Planet> create(@RequestBody Planet planet, UriComponentsBuilder ucBuilder){
		try {
			return new ResponseEntity<Planet>(service.createPlanet(planet), HttpStatus.CREATED);
		} catch (ConstraintViolationException ex) {
			return new ResponseEntity<Planet>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Planet>(HttpStatus.BAD_GATEWAY);
		}
		
	}
	
	@DeleteMapping(value="/{id}")
	@ApiOperation(value="Excluir Planeta", httpMethod="DELETE", code=200)
	public ResponseEntity<Void> remove(@PathVariable("id") String id){
		try {
			service.delete(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_GATEWAY);
		}
	}
	
}
