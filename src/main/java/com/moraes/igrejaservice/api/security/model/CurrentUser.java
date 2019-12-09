package com.moraes.igrejaservice.api.security.model;

import java.util.Collection;
import java.util.TreeSet;

import com.moraes.igrejaservice.api.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentUser {
	
	private String token;
	private Usuario user;
	private Collection<String> roles;
	
	public CurrentUser(String token, Usuario user) {
		roles = new TreeSet<>();
		user.getPermissoes().forEach(per -> roles.add(per.getNome().toString()));
		this.token = token;
		this.user = user;
	}
}
