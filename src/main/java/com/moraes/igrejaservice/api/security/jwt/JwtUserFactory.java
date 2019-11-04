package com.moraes.igrejaservice.api.security.jwt;

import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.moraes.igrejaservice.api.model.Usuario;
import com.moraes.igrejaservice.api.service.UsuarioService;

public class JwtUserFactory {
	private JwtUserFactory() {}
	
	public static JwtUser create(Usuario objeto, UsuarioService usuarioService) {
		return new JwtUser(objeto.getId() + "", objeto.getLogin(), objeto.getSenha(), mapToGrantedAthorities(objeto, usuarioService));
	}
	
	private static List<GrantedAuthority> mapToGrantedAthorities(Usuario usuario, UsuarioService usuarioService) {
		List<GrantedAuthority> listaGrantedAuthority = new LinkedList<>();
		usuario.getPermissoes().forEach(p -> listaGrantedAuthority.add(new SimpleGrantedAuthority(p.getNome())));
		return listaGrantedAuthority;
	}
}
