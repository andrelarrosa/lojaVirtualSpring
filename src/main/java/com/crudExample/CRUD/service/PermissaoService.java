package com.crudExample.CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.crudExample.CRUD.domain.Permissao;
import com.crudExample.CRUD.exception.BadResourceException;
import com.crudExample.CRUD.exception.ResourceAlreadyExistsException;
import com.crudExample.CRUD.exception.ResourceNotFoundException;
import com.crudExample.CRUD.repository.PermissaoRepository;

@Service
public class PermissaoService {
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	private boolean existsbyId(Long id) {
		return permissaoRepository.existsById(id);
	}
	
	public Permissao findById(Long id) throws ResourceNotFoundException {
		Permissao permissao = permissaoRepository.findById(id).orElse(null);
		if(permissao == null) {
			throw new ResourceNotFoundException("Permissão não foi encontrado com o id: "+id);
		}else {
			return permissao;
		}
	}
	
	public Page<Permissao> findAll(Pageable pageable){
		return permissaoRepository.findAll(pageable);
	}
	
	public Permissao save(Permissao permissao) throws BadResourceException, ResourceAlreadyExistsException{
		if(!StringUtils.isEmpty(permissao.getDescricao())) {
			if(permissao.getId() != 0 && existsbyId(permissao.getId())) {
				throw new ResourceAlreadyExistsException("Permissão com o id: "+permissao.getId()+"\n já existe");
			}
			return permissaoRepository.save(permissao);
		}else {
			BadResourceException exc = new BadResourceException("Erro ao salvar Permissão");
			exc.addErrorMessage("Permissão está vazio ou é nulo");
			throw exc;
		}
	}
	
	public void update(Permissao permissao) throws BadResourceException, ResourceNotFoundException{
		if(!StringUtils.isEmpty(permissao.getDescricao())) {
			if(!existsbyId(permissao.getId())) {
				throw new ResourceNotFoundException("Permissão não encontrado com o id: "+permissao.getId());
			}
			permissaoRepository.save(permissao);
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException{
		if(!existsbyId(id)) {
			throw new ResourceNotFoundException("Permissão não encontrado com o id: "+id);
		}else {
			permissaoRepository.deleteById(id);
		}
	}
	
	public Long count() {
		return permissaoRepository.count();
	}

}
