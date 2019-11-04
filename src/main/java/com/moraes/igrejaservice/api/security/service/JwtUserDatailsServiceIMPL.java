package com.moraes.igrejaservice.api.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moraes.igrejaservice.api.model.Usuario;
import com.moraes.igrejaservice.api.security.jwt.JwtUserFactory;
import com.moraes.igrejaservice.api.service.UsuarioService;

@Service
public class JwtUserDatailsServiceIMPL implements UserDetailsService{

	@Autowired
	private UsuarioService service;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		try {
			Usuario user = service.findByField("login", login);
			if(user == null) {
				throw new UsernameNotFoundException(String.format("Usuário não encontrado com o login \"%s\".", login));
			}
			else {
				return JwtUserFactory.create(user, service);
			}
		} catch (Exception e) {
			throw new UsernameNotFoundException(String.format("Usuário não encontrado com o login \"%s\".", login));
		}
	}
}
