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
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
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

import com.crudExample.CRUD.domain.Produto;
import com.crudExample.CRUD.domain.Usuario;
import com.crudExample.CRUD.dto.UsuarioDTO;
import com.crudExample.CRUD.exception.BadResourceException;
import com.crudExample.CRUD.exception.ResourceAlreadyExistsException;
import com.crudExample.CRUD.exception.ResourceNotFoundException;
import com.crudExample.CRUD.repository.UsuarioRepository;
import com.crudExample.CRUD.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


@RestController
@RequestMapping("/api")
@Tag(name="usuario", description="Api De Usuário")
public class UsuarioController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Operation(summary="Lista os Usuarios", description="Lista todos os usuários", tags={"usuario"})
	@ApiResponses(value= {@ApiResponse(responseCode = "200", description = "Registros encontrados"), @ApiResponse(responseCode = "500", description="Entre em contato com o suporte")})
	@GetMapping(value="/usuario")
	public ResponseEntity<Page<UsuarioDTO>> findAll(Pageable pageable){
		return ResponseEntity.ok(new UsuarioDTO().converterListaUsuarioDTO(usuarioService.findAll(pageable)));
	}
	
	@Operation(summary="Cadastrar Usuário", description="Cadastra o usuario", tags={"usuario"})
	@ApiResponses(value= {@ApiResponse(responseCode = "200", description = "Cadastrado com sucesso"), @ApiResponse(responseCode = "500", description="Não foi possível de cadastrar este usuário")})
	@PostMapping(value="/usuario")
	public ResponseEntity<UsuarioDTO> addUsuario(@RequestBody Usuario usuario) throws URISyntaxException{
		try {
			Usuario u = usuarioService.save(usuario);
			return ResponseEntity.created(new URI("/api/usuario/"+u.getId())).body(new UsuarioDTO().converter(usuario));
		}catch(ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}catch(BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	@Operation(summary="Alterar Usuario", description="Altera o usuario por id", tags={"usuario"})
	@ApiResponses(value= {@ApiResponse(responseCode = "200", description = "Alterado com sucesso"), @ApiResponse(responseCode = "404", description="Usuário não encontrado")})
	@PutMapping(value="/usuario/{id}")
	public ResponseEntity<Usuario> updateUsuario(@Valid @RequestBody Usuario usuario, @PathVariable long id) throws BadResourceException{
		try {
			usuario.setId(id);
			usuarioService.update(usuario);
			return ResponseEntity.ok().build();
		}catch(ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		}catch(BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@Operation(summary="Deletar Usuario", description="Deleta o usuario", tags={"usuario"})
	@ApiResponses(value= {@ApiResponse(responseCode = "200", description = "Deletado com sucesso"), @ApiResponse(responseCode = "404", description="Usuário não encontrado")})
	@DeleteMapping(path="/usuario/{id}")
	public ResponseEntity<Produto> deleteUsuarioById(@PathVariable long id){
		try {
			usuarioService.deleteById(id);
			return ResponseEntity.ok().build();
		}catch(ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}
}
