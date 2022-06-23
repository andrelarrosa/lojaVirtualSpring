package com.crudExample.CRUD.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import com.crudExample.CRUD.domain.Usuario;

import lombok.Data;

@Data
public class UsuarioDTO {
	private String nome;
	private String cpf;
	private String email;

	public UsuarioDTO converter (Usuario usuario) {
		BeanUtils.copyProperties(usuario, this);
		return this;
	}
	
	public Page<UsuarioDTO> converterListaUsuarioDTO(Page<Usuario> pageUsuario){
		Page<UsuarioDTO> listaDTO = pageUsuario.map(this::converter);
		return listaDTO;
	}
}
