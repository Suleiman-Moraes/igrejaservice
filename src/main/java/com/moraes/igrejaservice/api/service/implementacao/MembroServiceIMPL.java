package com.moraes.igrejaservice.api.service.implementacao;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moraes.igrejaservice.api.model.Membro;
import com.moraes.igrejaservice.api.persistencia.hql.GenericDAO;
import com.moraes.igrejaservice.api.persistencia.jpa.MembroDAO;
import com.moraes.igrejaservice.api.service.MembroService;

import lombok.Getter;

@Getter
@Service
public class MembroServiceIMPL implements MembroService{

	private static final Log logger = LogFactory.getLog(MembroService.class);
	
	@Autowired
	private MembroDAO persistencia;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Override
	public Membro findByField(String field, Object value) {
		try {
			Membro objeto = genericDAO.findByField(Membro.class, field, value);
			return objeto;
		} catch (Exception e) {
			logger.warn("findByField " + e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<String> validar(Membro objeto) {
		List<String> erros = new LinkedList<>();
//		erros = ValidacaoComumUtil.validarString(objeto.getNome(), "Nome", 'o', erros, 255);
//		erros = ValidacaoComumUtil.validarString(objeto.getDescricao(), "Descrição", 'a', erros, 255);
//		erros = ValidacaoComumUtil.validarNotNullAndMaiorZero(objeto.getUsuario(), "Usuario", 'o', erros);
		return erros;
	}
	
	@Override
	public Log getLogger() {
		return logger;
	}
}
