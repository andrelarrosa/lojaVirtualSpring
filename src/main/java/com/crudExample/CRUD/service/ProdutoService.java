package com.crudExample.CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.crudExample.CRUD.domain.Produto;
import com.crudExample.CRUD.exception.BadResourceException;
import com.crudExample.CRUD.exception.ResourceAlreadyExistsException;
import com.crudExample.CRUD.exception.ResourceNotFoundException;
import com.crudExample.CRUD.repository.ProdutoRepository;


@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	private boolean existsbyId(Long id) {
		return produtoRepository.existsById(id);
	}
	
	public Produto findById(Long id) throws ResourceNotFoundException {
		Produto produto = produtoRepository.findById(id).orElse(null);
		if(produto == null) {
			throw new ResourceNotFoundException("Produto não foi encontrado com o id: "+id);
		}else {
			return produto;
		}
	}
	
	public Page<Produto> findAll(Pageable pageable){
		return produtoRepository.findAll(pageable);
	}
	
	public Produto save(Produto produto) throws BadResourceException, ResourceAlreadyExistsException{
		if(!StringUtils.isEmpty(produto.getNome())) {
			if(produto.getId() != null && existsbyId(produto.getId())) {
				throw new ResourceAlreadyExistsException("Produto com o id: "+produto.getId()+"\n já existe");
			}
			return produtoRepository.save(produto);
		}else {
			BadResourceException exc = new BadResourceException("Erro ao salvar produto");
			exc.addErrorMessage("Produto está vazio ou é nulo");
			throw exc;
		}
	}
	
	public void update(Produto produto) throws BadResourceException, ResourceNotFoundException{
		if(!StringUtils.isEmpty(produto.getNome())) {
			if(!existsbyId(produto.getId())) {
				throw new ResourceNotFoundException("Produto não encontrado com o id: "+produto.getId());
			}
			produtoRepository.save(produto);
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException{
		if(!existsbyId(id)) {
			throw new ResourceNotFoundException("Produto não encontrado com o id: "+id);
		}else {
			produtoRepository.deleteById(id);
		}
	}
	
	public Long count() {
		return produtoRepository.count();
	}

}
