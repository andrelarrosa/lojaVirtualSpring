package com.crudExample.CRUD.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crudExample.CRUD.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	// Page<Produto> findByNome(String nome, Pageable page);
	@Query(value = "from Produto")
	Page<Produto> findAll(Pageable page);
}

