package com.moraes.igrejaservice.api.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.moraes.igrejaservice.api.model.Membro;
import com.moraes.igrejaservice.api.model.dto.MembroDto;
import com.moraes.igrejaservice.api.model.interfaces.IMembro;
import com.moraes.igrejaservice.api.util.CRUDPadraoService;

@Component
public interface MembroService extends CRUDPadraoService<Membro>{

	List<String> validar(IMembro objeto);

	MembroDto save(MembroDto objeto);

	Membro save(Membro objeto, String token) throws Exception;
}
