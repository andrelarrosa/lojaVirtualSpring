package com.crudExample.CRUD.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class Usuario {
private static final long serialVersionUID = 4048798961366546485L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotBlank
	@Schema(description = "Nome do usuário",example = "André Luiz Gonçalves Larrosa",required = true)
	private String nome;
	
	@Lob
	private byte[] imagemPerfil;
	
	@Schema(description = "CPF do usuário",example = "124.968.929-50",required = true)
	private String cpf;
	private String email;
	private String senha;
	
	@CreationTimestamp
	private Timestamp dataCadastro;
	
	@UpdateTimestamp
	private Timestamp dataModificacao;
}
