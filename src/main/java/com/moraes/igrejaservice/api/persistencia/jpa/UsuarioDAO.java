package com.moraes.igrejaservice.api.persistencia.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moraes.igrejaservice.api.model.Usuario;


public interface UsuarioDAO extends JpaRepository<Usuario, Long>{
	
	List<Usuario> findByLoginAndIdNot(String login, Long id);
}
