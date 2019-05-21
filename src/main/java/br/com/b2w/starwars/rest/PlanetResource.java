package br.com.b2w.starwars.rest;

import static br.com.b2w.starwars.config.Constants.JSON;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.b2w.starwars.model.Planet;
import br.com.b2w.starwars.service.PlanetService;
import io.swagger.annotations.ApiOperation;

/**
 * Recurso relativo as requisições para a URI /planets
 * 
 * @author Filipe Oliveira
 *
 */
@RestController
@RequestMapping(value="/planets", produces=MediaType.APPLICATION_JSON_VALUE)
public class PlanetResource {
	
	@Autowired
	PlanetService service;
	
	@GetMapping
	@ApiOperation(value="Listar", httpMethod="GET", response=Planet.class, responseContainer="List", code=200)
	public ResponseEntity<List<Planet>> list(@RequestParam(name="nome", required=false) String nome){
		return new ResponseEntity<List<Planet>>(service.list(nome), HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	@ApiOperation(value="Consultar", httpMethod="GET", response=Planet.class, code=302)
	public ResponseEntity<Planet> fetch(@PathVariable("id") String id){

		try {			
			return new ResponseEntity<Planet>(service.fetch(id), HttpStatus.OK);
		} catch (EmptyResultDataAccessException ex) {
			return new ResponseEntity<Planet>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<Planet>(HttpStatus.BAD_GATEWAY);
		}
		
	}
	
	@PostMapping
	@ApiOperation(value="Criar", httpMethod="POST", response=Planet.class, code=201)
	public ResponseEntity<Void> create(@RequestBody Planet planet, UriComponentsBuilder ucBuilder){
		try {
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (ConstraintViolationException ex) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_GATEWAY);
		}
		
	}
	
	@DeleteMapping(value="/{id}")
	@ApiOperation(value="Excluir", httpMethod="DELETE", code=204)
	public ResponseEntity<Void> remove(@PathVariable("id") String id){
		try {
			service.delete(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_GATEWAY);
		}
	}
	
}
