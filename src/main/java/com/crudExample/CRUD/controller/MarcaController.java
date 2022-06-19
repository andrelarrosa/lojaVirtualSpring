package com.crudExample.CRUD.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.crudExample.CRUD.domain.Marca;
import com.crudExample.CRUD.domain.Produto;
import com.crudExample.CRUD.exception.BadResourceException;
import com.crudExample.CRUD.exception.ResourceAlreadyExistsException;
import com.crudExample.CRUD.exception.ResourceNotFoundException;
import com.crudExample.CRUD.service.MarcaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name="marca", description="Api De Marca")
public class MarcaController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MarcaService marcaService;
	
	@GetMapping(value="/marca")
	public ResponseEntity<Page<Marca>> findAll(Pageable pageable){
		return ResponseEntity.ok(marcaService.findAll(pageable));
	}
	
	@PostMapping(value="/marca")
	public ResponseEntity<Marca> addProduto(@RequestBody Marca marca) throws URISyntaxException{
		try {
			Marca m = marcaService.save(marca);
			return ResponseEntity.created(new URI("/api/produto/"+m.getId())).body(marca);
		}catch(ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}catch(BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PutMapping(value="/marca/{id}")
	public ResponseEntity<Marca> updateProduto(@Valid @RequestBody Marca marca, @PathVariable long id) throws BadResourceException{
		try {
			marca.setId(id);
			marcaService.update(marca);
			return ResponseEntity.ok().build();
		}catch(ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		}catch(BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@DeleteMapping(path="/marca/{id}")
	public ResponseEntity<Marca> deleteProdutoById(@PathVariable long id){
		try {
			marcaService.deleteById(id);
			return ResponseEntity.ok().build();
		}catch(ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}

}
