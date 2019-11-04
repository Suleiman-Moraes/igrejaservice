package com.moraes.igrejaservice.api.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moraes.igrejaservice.api.model.Membro;

public interface MembroDAO extends JpaRepository<Membro, Long>{
	
}
