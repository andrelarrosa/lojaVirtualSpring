package com.crudExample.CRUD.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crudExample.CRUD.domain.Usuario;
import com.crudExample.CRUD.dto.UsuarioDTO;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	// Page<Produto> findByNome(String nome, Pageable page);
	@Query(value = "from Usuario")
	Page<Usuario> findAll(Pageable page);
}

