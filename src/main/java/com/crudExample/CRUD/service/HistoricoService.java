package com.crudExample.CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.crudExample.CRUD.domain.Historico;
import com.crudExample.CRUD.exception.BadResourceException;
import com.crudExample.CRUD.exception.ResourceAlreadyExistsException;
import com.crudExample.CRUD.exception.ResourceNotFoundException;
import com.crudExample.CRUD.repository.HistoricoRepository;

@Service
public class HistoricoService {
	@Autowired
	private HistoricoRepository historicoRepository;
	

	private boolean existsbyId(Long id) {
		return historicoRepository.existsById(id);
	}
	
	public Historico save(Historico historico) throws BadResourceException, ResourceAlreadyExistsException{
		if(historico.getId() != null) {
			if(historico.getId() != 0 && existsbyId(historico.getId())) {
				throw new ResourceAlreadyExistsException("Historico com o id: "+historico.getId()+"\n já existe");
			}
			return historicoRepository.save(historico);
		}else {
			BadResourceException exc = new BadResourceException("Erro ao salvar historico");
			exc.addErrorMessage("Historico está vazio ou é nulo");
			throw exc;
		}
	}

}
