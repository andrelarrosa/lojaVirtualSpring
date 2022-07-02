package com.crudExample.CRUD.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crudExample.CRUD.domain.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Long>{
	@Query(value = "from Historico")
	Page<Historico> findAll(Pageable page);

}
