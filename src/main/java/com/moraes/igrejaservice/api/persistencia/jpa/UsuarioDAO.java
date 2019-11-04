package com.moraes.igrejaservice.api.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moraes.igrejaservice.api.model.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Long>{
	
}
