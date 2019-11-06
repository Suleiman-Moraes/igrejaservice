package com.moraes.igrejaservice.api.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.moraes.igrejaservice.api.model.Permissao;
import com.moraes.igrejaservice.api.model.enumeration.TipoMembroEnum;
import com.moraes.igrejaservice.api.util.CRUDPadraoService;

@Component
public interface PermissaoService extends CRUDPadraoService<Permissao>{

	List<Permissao> getPermissoesByTipoMembro(TipoMembroEnum tipo);
	
}
