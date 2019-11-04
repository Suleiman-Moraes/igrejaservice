package com.moraes.igrejaservice.api.model.interfaces;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.moraes.igrejaservice.api.response.Response;
import com.moraes.igrejaservice.api.util.CRUDPadraoService;

public interface IControlador<T> {
    ResponseEntity<Response<List<T>>> findAll(HttpServletRequest request);
 
    ResponseEntity<Response<T>> findById(HttpServletRequest request, String id);
 
    ResponseEntity<Response<T>> newObject(HttpServletRequest request, T objeto);
 
    ResponseEntity<Response<T>> update(HttpServletRequest request, T objeto);
 
    ResponseEntity<Response<Boolean>> deleteById(HttpServletRequest request, String id);
    
    ResponseEntity<Response<T>> findByField(HttpServletRequest request, String field, String value);
    
    ResponseEntity<Response<Boolean>> existsByField(HttpServletRequest request, String field, String value);
    
    CRUDPadraoService<T> getService();
}
