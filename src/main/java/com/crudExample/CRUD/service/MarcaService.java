package com.crudExample.CRUD.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.crudExample.CRUD.domain.Marca;
import com.crudExample.CRUD.exception.BadResourceException;
import com.crudExample.CRUD.exception.ResourceAlreadyExistsException;
import com.crudExample.CRUD.exception.ResourceNotFoundException;
import com.crudExample.CRUD.repository.MarcaRepository;

@Service
public class MarcaService {
	private MarcaRepository marcaRepository;
	
	private boolean existsbyId(Long id) {
		return marcaRepository.existsById(id);
	}
	
	public Marca findById(Long id) throws ResourceNotFoundException {
		Marca marca = marcaRepository.findById(id).orElse(null);
		if(marca == null) {
			throw new ResourceNotFoundException("Marca não foi encontrado com o id: "+id);
		}else {
			return marca;
		}
	}
	
	public Page<Marca> findAll(Pageable pageable){
		return marcaRepository.findAll(pageable);
	}
	
	public Marca save(Marca marca) throws BadResourceException, ResourceAlreadyExistsException{
		if(!StringUtils.isEmpty(marca.getDescricao())) {
			if(marca.getId() != 0 && existsbyId(marca.getId())) {
				throw new ResourceAlreadyExistsException("Marca com o id: "+marca.getId()+"\n já existe");
			}
			return marcaRepository.save(marca);
		}else {
			BadResourceException exc = new BadResourceException("Erro ao salvar marca");
			exc.addErrorMessage("Marca está vazio ou é nulo");
			throw exc;
		}
	}
	
	public void update(Marca marca) throws BadResourceException, ResourceNotFoundException{
		if(!StringUtils.isEmpty(marca.getDescricao())) {
			if(!existsbyId(marca.getId())) {
				throw new ResourceNotFoundException("Marca não encontrado com o id: "+marca.getId());
			}
			marcaRepository.save(marca);
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException{
		if(!existsbyId(id)) {
			throw new ResourceNotFoundException("Marca não encontrado com o id: "+id);
		}else {
			marcaRepository.deleteById(id);
		}
	}
	
	public Long count() {
		return marcaRepository.count();
	}
}
