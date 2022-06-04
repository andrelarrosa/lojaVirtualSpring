package com.crudExample.CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.crudExample.CRUD.domain.Produto;
import com.crudExample.CRUD.domain.Usuario;
import com.crudExample.CRUD.exception.BadResourceException;
import com.crudExample.CRUD.exception.ResourceAlreadyExistsException;
import com.crudExample.CRUD.exception.ResourceNotFoundException;
import com.crudExample.CRUD.repository.ProdutoRepository;
import com.crudExample.CRUD.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private boolean existsbyId(Long id) {
		return usuarioRepository.existsById(id);
	}
	
	public Usuario findById(Long id) throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		if(usuario== null) {
			throw new ResourceNotFoundException("Usuário não foi encontrado com o id: "+id);
		}else {
			return usuario;
		}
	}
	
	public Page<Usuario> findAll(Pageable pageable){
		return usuarioRepository.findAll(pageable);
	}
	
	public Usuario save(Usuario usuario) throws BadResourceException, ResourceAlreadyExistsException{
		if(!StringUtils.isEmpty(usuario.getNome())) {
			if(usuario.getId() != null && existsbyId(usuario.getId())) {
				throw new ResourceAlreadyExistsException("Usuário com o id: "+usuario.getId()+"\n já existe");
			}
			return usuarioRepository.save(usuario);
		}else {
			BadResourceException exc = new BadResourceException("Erro ao salvar Usuário");
			exc.addErrorMessage("Usuário está vazio ou é nulo");
			throw exc;
		}
	}
	
	public void update(Usuario usuario) throws BadResourceException, ResourceNotFoundException{
		if(!StringUtils.isEmpty(usuario.getNome())) {
			if(!existsbyId(usuario.getId())) {
				throw new ResourceNotFoundException("Usuário não encontrado com o id: "+usuario.getId());
			}
			usuarioRepository.save(usuario);
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException{
		if(!existsbyId(id)) {
			throw new ResourceNotFoundException("Usuário não encontrado com o id: "+id);
		}else {
			usuarioRepository.deleteById(id);
		}
	}
	
	public Long count() {
		return usuarioRepository.count();
	}
}
