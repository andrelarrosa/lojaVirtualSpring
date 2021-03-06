package com.crudExample.CRUD.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Table(name = "produto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class Produto {
	private static final long serialVersionUID = 4048798961366546485L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nome;
	
	private double precoVenda;
	private double valorCusto;
	private double quantidadeEstoque;
	@ManyToOne
	@JoinColumn(name="idMarca")
	private Marca marca;
	@ManyToOne
	@JoinColumn(name="idCategoria")
	private Categoria categoria;
	
	@CreationTimestamp
	private Timestamp dataCadastro;
	
	@UpdateTimestamp
	private Timestamp dataModificacao;
	
	
}
