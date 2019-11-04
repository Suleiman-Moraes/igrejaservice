package com.moraes.igrejaservice.api.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.moraes.igrejaservice.api.model.Endereco;
import com.moraes.igrejaservice.api.util.CRUDPadraoService;

@Component
public interface EnderecoService extends CRUDPadraoService<Endereco>{

	List<String> validar(Endereco objeto, List<String> erros);
}
