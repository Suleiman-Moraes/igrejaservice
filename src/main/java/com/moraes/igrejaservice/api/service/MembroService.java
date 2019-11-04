package com.moraes.igrejaservice.api.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.moraes.igrejaservice.api.model.Membro;
import com.moraes.igrejaservice.api.util.CRUDPadraoService;

@Component
public interface MembroService extends CRUDPadraoService<Membro>{

	List<String> validar(Membro objeto);
}
