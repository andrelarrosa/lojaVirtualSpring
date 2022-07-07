package com.crudExample.CRUD.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crudExample.CRUD.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	// Page<Produto> findByNome(String nome, Pageable page);
	@Query(value = "from Produto a where a.id = ?1")
	Produto buscarPorId(Long id);
	
	@Query(value = "select a from Produto a")
	Page<Produto> findAll(Pageable page);
	
	// @Query(nativeQuery = true, value="SELECT p.* FROM produto p LEFT JOIN categoria c ON p.categoria_id = c.id WHERE p.categoria_id = ?1") query SQL faz assim
	@Query(value="select p from Produto p where p.categoria.id = ?1")
	public List<Produto> buscarProdutosCategoria(Long id);
	
}

