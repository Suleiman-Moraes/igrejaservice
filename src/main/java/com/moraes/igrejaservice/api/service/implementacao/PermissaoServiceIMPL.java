package com.moraes.igrejaservice.api.service.implementacao;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moraes.igrejaservice.api.model.Permissao;
import com.moraes.igrejaservice.api.model.enumeration.TipoMembroEnum;
import com.moraes.igrejaservice.api.persistencia.hql.GenericDAO;
import com.moraes.igrejaservice.api.persistencia.jpa.PermissaoDAO;
import com.moraes.igrejaservice.api.service.PermissaoService;

import lombok.Getter;

@Getter
@Service
public class PermissaoServiceIMPL implements PermissaoService{

	private static final Log logger = LogFactory.getLog(PermissaoService.class);
	
	@Autowired
	private PermissaoDAO persistencia;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Override
	public Permissao findByField(String field, Object value) {
		try {
			Permissao objeto = genericDAO.findByField(Permissao.class, field, value);
			return objeto;
		} catch (Exception e) {
			logger.warn("findByField " + e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<Permissao> getPermissoesByTipoMembro(TipoMembroEnum tipo){
		try {
			List<Long> ids = new LinkedList<>();
			ids.add(1l);
			switch (tipo) {
			case PASTOR_DIRIGENTE:
				ids.add(2l);
				ids.add(3l);
				break;
			case TESOUREIRO:
				ids.add(3l);
				break;
			default:
				break;
			}
			return persistencia.findAllById(ids);
		} catch (Exception e) {
			logger.warn("getPermissoesByTipoMembro " + e.getMessage());
			return null;
		}
	}
	
	@Override
	public Log getLogger() {
		return logger;
	}
}
