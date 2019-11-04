package com.moraes.igrejaservice.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moraes.igrejaservice.api.model.Usuario;
import com.moraes.igrejaservice.api.security.jwt.JwtAuthenticationRequest;
import com.moraes.igrejaservice.api.security.jwt.JwtTokenUtil;
import com.moraes.igrejaservice.api.security.model.CurrentUser;
import com.moraes.igrejaservice.api.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationRestController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UsuarioService userService;

	@PostMapping(value = "/auth")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
			throws AuthenticationException {
		final String login = authenticationRequest.getLogin();
		final String password = authenticationRequest.getPassword();
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getLogin());
		final String token = jwtTokenUtil.generateToken(userDetails);
		final Usuario user = userService.findByField("login", authenticationRequest.getLogin());
		return ResponseEntity.ok(new CurrentUser(token, user));
	}
	
	@GetMapping(value = "/refresh")
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String username = jwtTokenUtil.getUsernameFromToken(token);
		final Usuario user = userService.findByField("login", username);
		
		if (jwtTokenUtil.canTokenBeRefreshed(token)) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(new CurrentUser(refreshedToken, user));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@GetMapping(value = "/auth")
	public ResponseEntity<Boolean> isTokenIsValid(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String username = jwtTokenUtil.getUsernameFromToken(token);
		final Usuario user = userService.findByField("login", username);

		if (user != null && user.getId() != null && !jwtTokenUtil.isTokenExpired(token)) {
			return ResponseEntity.ok(Boolean.TRUE);
		} else {
			return ResponseEntity.badRequest().body(Boolean.FALSE);
		}
	}
}
