package com.moraes.igrejaservice.api.service.implementacao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moraes.igrejaservice.api.model.Endereco;
import com.moraes.igrejaservice.api.persistencia.hql.GenericDAO;
import com.moraes.igrejaservice.api.persistencia.jpa.EnderecoDAO;
import com.moraes.igrejaservice.api.service.EnderecoService;
import com.moraes.igrejaservice.api.util.ValidacaoComumUtil;

import lombok.Getter;

@Getter
@Service
public class EnderecoServiceIMPL implements EnderecoService{

	private static final Log logger = LogFactory.getLog(EnderecoService.class);
	
	@Autowired
	private EnderecoDAO persistencia;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Override
	public Endereco findByField(String field, Object value) {
		try {
			Endereco objeto = genericDAO.findByField(Endereco.class, field, value);
			return objeto;
		} catch (Exception e) {
			logger.warn("findByField " + e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<String> validar(Endereco objeto, List<String> erros) {
		erros = ValidacaoComumUtil.validarString(objeto.getSetor(), "Setor", 'o', erros, 255);
		erros = ValidacaoComumUtil.validarString(objeto.getCidade(), "Cidade", 'a', erros, 255);
		erros = ValidacaoComumUtil.validarNotNull(objeto.getUf(), "Uf", 'a', erros);
		return erros;
	}
	
	@Override
	public Log getLogger() {
		return logger;
	}
}
