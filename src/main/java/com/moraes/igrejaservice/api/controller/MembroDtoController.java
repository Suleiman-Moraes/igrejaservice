package com.moraes.igrejaservice.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moraes.igrejaservice.api.model.dto.MembroDto;
import com.moraes.igrejaservice.api.response.Response;
import com.moraes.igrejaservice.api.service.MembroService;
import com.moraes.igrejaservice.api.util.FacesUtil;
import com.moraes.igrejaservice.api.util.RestControllerUtil;

@RestController
@RequestMapping("/api/membro/dto")
public class MembroDtoController{

	@Autowired
	private MembroService service;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('PASTOR')")
	public ResponseEntity<Response<MembroDto>> newObject(HttpServletRequest request, @RequestBody MembroDto objeto) {
		Response<MembroDto> response = new Response<>();
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
	@PreAuthorize("hasAnyRole('PASTOR')")
	public ResponseEntity<Response<MembroDto>> update(HttpServletRequest request, @RequestBody MembroDto objeto) {
		Response<MembroDto> response = new Response<>();
		try {
			if(objeto.getId() == null) {
				throw new Exception(FacesUtil.propertiesLoader().getProperty("membroNaoExiste"));
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
