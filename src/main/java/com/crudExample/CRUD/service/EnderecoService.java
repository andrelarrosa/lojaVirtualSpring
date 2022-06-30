package com.crudExample.CRUD.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.crudExample.CRUD.domain.Endereco;

@Service
public class EnderecoService {
	
	public Endereco encontrarEnderecoPorCEP(String cep) {
		String url = "https://brasilapi.com.br/api/cep/v2/"+cep;
		RestTemplate rest = new RestTemplate();
		Endereco endereco = rest.getForObject(url, Endereco.class);
		return endereco;
	}
}
