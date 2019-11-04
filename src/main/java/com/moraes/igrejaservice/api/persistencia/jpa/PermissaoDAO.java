package com.moraes.igrejaservice.api.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moraes.igrejaservice.api.model.Permissao;

public interface PermissaoDAO extends JpaRepository<Permissao, Long>{
	
}
