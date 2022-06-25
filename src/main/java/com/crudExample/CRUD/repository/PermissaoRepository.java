package com.crudExample.CRUD.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crudExample.CRUD.domain.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
	@Query(value = "from Permissao")
	Page<Permissao> findAll(Pageable page);
}
