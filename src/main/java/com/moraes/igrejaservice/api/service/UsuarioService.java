package com.moraes.igrejaservice.api.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.moraes.igrejaservice.api.model.Membro;
import com.moraes.igrejaservice.api.model.Usuario;
import com.moraes.igrejaservice.api.util.CRUDPadraoService;

@Component
public interface UsuarioService extends CRUDPadraoService<Usuario>{

	List<String> validar(Usuario objeto);

	Usuario saveNewUserByMembro(Membro objeto);
}
