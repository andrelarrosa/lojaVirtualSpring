package com.crudExample.CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.crudExample.CRUD.domain.PermissaoUsuario;
import com.crudExample.CRUD.exception.BadResourceException;
import com.crudExample.CRUD.exception.ResourceAlreadyExistsException;
import com.crudExample.CRUD.exception.ResourceNotFoundException;
import com.crudExample.CRUD.repository.PermissaoUsuarioRepository;


@Service
public class PermissaoUsuarioService {
	
	@Autowired
	private PermissaoUsuarioRepository permissaoUsuarioRepository;
	
	private boolean existsbyId(Long id) {
		return permissaoUsuarioRepository.existsById(id);
	}
	
	public PermissaoUsuario findById(Long id) throws ResourceNotFoundException {
		PermissaoUsuario permissaoUsuario = permissaoUsuarioRepository.findById(id).orElse(null);
		if(permissaoUsuario == null) {
			throw new ResourceNotFoundException("Produto não foi encontrado com o id: "+id);
		}else {
			return permissaoUsuario;
		}
	}
	
	public Page<PermissaoUsuario> findAll(Pageable pageable){
		return permissaoUsuarioRepository.findAll(pageable);
	}
	
	public PermissaoUsuario save(PermissaoUsuario permissaoUsuario) throws BadResourceException, ResourceAlreadyExistsException{
		if(permissaoUsuario.getId() != null) {
			if(existsbyId(permissaoUsuario.getId())) {
				throw new ResourceAlreadyExistsException("Permissao Usuario com o id: "+permissaoUsuario.getId()+"\n já existe");
			}
			return permissaoUsuarioRepository.save(permissaoUsuario);			
		}else {
			BadResourceException exc = new BadResourceException("Erro ao salvar Permissao Usuario");
			exc.addErrorMessage("Permissao Usuario está vazio ou é nulo");
			throw exc;
		}
	}
	
	public void update(PermissaoUsuario permissaoUsuario) throws BadResourceException, ResourceNotFoundException{
		if(permissaoUsuario.getId() != null) {
			if(!existsbyId(permissaoUsuario.getId())) {
				throw new ResourceNotFoundException("Produto não encontrado com o id: "+permissaoUsuario.getId());
			}
			permissaoUsuarioRepository.save(permissaoUsuario);
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException{
		if(!existsbyId(id)) {
			throw new ResourceNotFoundException("Produto não encontrado com o id: "+id);
		}else {
			permissaoUsuarioRepository.deleteById(id);
		}
	}
	
	public Long count() {
		return permissaoUsuarioRepository.count();
	}
}
