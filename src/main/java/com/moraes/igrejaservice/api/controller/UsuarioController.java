package com.moraes.igrejaservice.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moraes.igrejaservice.api.controller.abstracts.ManterControllerBeanBasic;
import com.moraes.igrejaservice.api.model.Usuario;
import com.moraes.igrejaservice.api.response.Response;
import com.moraes.igrejaservice.api.service.UsuarioService;
import com.moraes.igrejaservice.api.util.FacesUtil;
import com.moraes.igrejaservice.api.util.RestControllerUtil;

import lombok.Getter;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController extends ManterControllerBeanBasic<Usuario>{

	@Getter
	@Autowired
	private UsuarioService service;
	
	@PostMapping
	public ResponseEntity<Response<Usuario>> newObject(HttpServletRequest request, @RequestBody Usuario objeto) {
		Response<Usuario> response = new Response<>();
		try {
			List<String> erros = service.validar(objeto);
			if(erros.size() > 0) {
				response.setErros(erros);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			response.setData(service.save(objeto));
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return RestControllerUtil.mostrarErroPadraoObject(this.getClass(), response, e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<Response<Usuario>> update(HttpServletRequest request, @RequestBody Usuario objeto) {
		Response<Usuario> response = new Response<>();
		try {
			if(objeto.getId() == null) {
				throw new Exception(FacesUtil.propertiesLoader().getProperty("prazoNaoExiste"));
			}
			List<String> erros = service.validar(objeto);
			if(erros.size() > 0) {
				response.setErros(erros);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			response.setData(service.save(objeto));
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return RestControllerUtil.mostrarErroPadraoObject(this.getClass(), response, e.getMessage());
		}
	}
}
