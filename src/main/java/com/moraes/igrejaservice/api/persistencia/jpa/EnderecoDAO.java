package com.moraes.igrejaservice.api.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moraes.igrejaservice.api.model.Endereco;

public interface EnderecoDAO extends JpaRepository<Endereco, Long>{
	
}
