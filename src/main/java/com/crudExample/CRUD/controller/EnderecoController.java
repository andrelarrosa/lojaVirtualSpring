package com.crudExample.CRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudExample.CRUD.domain.Endereco;
import com.crudExample.CRUD.service.EnderecoService;

@RestController
@RequestMapping("/api")
public class EnderecoController {
	
	@Autowired
	private EnderecoService brasilApiService;
	
	@GetMapping(value="/cep/{cep}")
	public Endereco encontrarEnderecoPorCEP(@PathVariable String cep) {
		Endereco endereco = brasilApiService.encontrarEnderecoPorCEP(cep);
		return endereco;
	}
}
