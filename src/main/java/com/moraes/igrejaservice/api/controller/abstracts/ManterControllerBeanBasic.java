package com.moraes.igrejaservice.api.controller.abstracts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.moraes.igrejaservice.api.model.interfaces.IControlador;
import com.moraes.igrejaservice.api.response.Response;
import com.moraes.igrejaservice.api.util.RestControllerUtil;

public abstract class ManterControllerBeanBasic<T> implements IControlador<T>{
	
	@GetMapping
	@Override
    public ResponseEntity<Response<List<T>>> findAll(HttpServletRequest request) {
        return RestControllerUtil.findAllCompleto(getService());
    }
 
    @GetMapping(value = "{id}")
	@Override
    public ResponseEntity<Response<T>> findById(HttpServletRequest request, @PathVariable("id") String id) {
    	return RestControllerUtil.findByIdCompleto(getService(), id);
    }
 
    @PostMapping
	@Override
    public ResponseEntity<Response<T>> newObject(HttpServletRequest request, @RequestBody T objeto) {
    	return RestControllerUtil.saveCompleto(getService(), objeto);
    }
 
    @PutMapping
    public ResponseEntity<Response<T>> update(HttpServletRequest request, @RequestBody T objeto) {
    	return RestControllerUtil.saveCompleto(getService(), objeto);
    }
 
    @DeleteMapping(value = "{id}")
	@Override
    public ResponseEntity<Response<Boolean>> deleteById(HttpServletRequest request, @PathVariable("id") String id) {
    	return RestControllerUtil.deleteByIdCompleto(getService(), id);
    }
    
    @GetMapping(value = "/findbyfield")
	@Override
    public ResponseEntity<Response<T>> findByField(HttpServletRequest request, @RequestParam("field") String field, 
    		@RequestParam("value") String value) {
    	return RestControllerUtil.findByFieldCompleto(getService(), field, value);
    }
    
    @GetMapping(value = "/exists")
    @Override
    public ResponseEntity<Response<Boolean>> existsByField(HttpServletRequest request, @RequestParam("field") String field, 
    		@RequestParam("value") String value) {
    	return RestControllerUtil.existsByFieldCompleto(getService(), field, value);
    }
    
    @GetMapping(value = "/findbyparamssingle")
	public ResponseEntity<Response<Page<?>>> findByParams(HttpServletRequest request,
			@RequestParam("page") Integer page, @RequestParam("count") Integer count) {
    	return RestControllerUtil.findByParams(getService(), page, count);
	}
}
