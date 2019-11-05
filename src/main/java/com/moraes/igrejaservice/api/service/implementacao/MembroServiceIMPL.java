package com.moraes.igrejaservice.api.service.implementacao;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.moraes.igrejaservice.api.model.Membro;
import com.moraes.igrejaservice.api.model.dto.MembroDto;
import com.moraes.igrejaservice.api.model.interfaces.IMembro;
import com.moraes.igrejaservice.api.persistencia.hql.GenericDAO;
import com.moraes.igrejaservice.api.persistencia.jpa.MembroDAO;
import com.moraes.igrejaservice.api.service.EnderecoService;
import com.moraes.igrejaservice.api.service.MembroService;
import com.moraes.igrejaservice.api.service.UsuarioService;
import com.moraes.igrejaservice.api.util.FacesUtil;
import com.moraes.igrejaservice.api.util.ValidacaoComumUtil;

import lombok.Getter;

@Getter
@Service
public class MembroServiceIMPL implements MembroService{

	private static final Log logger = LogFactory.getLog(MembroService.class);
	
	@Autowired
	private MembroDAO persistencia;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EnderecoService enderecoService;
	
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
	public MembroDto save(MembroDto objeto) {
		try {
			return new MembroDto(save(new Membro(objeto)));
		} catch (Exception e) {
			logger.warn("findByField " + e.getMessage());
			return null;
		}
	}
	
	@Override
	public Membro save(Membro objeto) throws Exception {
		try {
			objeto = persistencia.save(objeto);
			posSave(objeto);
			return objeto;
		} catch (DataIntegrityViolationException e) {
			logger.error("save " + e.getMessage());
			throw new Exception(FacesUtil.propertiesLoader().getProperty("dadosInvalidos"));
		} catch (Exception e) {
			logger.error("save " + e.getMessage());
			throw e;
		}
	}
	
	@Override
	public List<String> validar(IMembro objeto) {
		List<String> erros = new LinkedList<>();
		erros = ValidacaoComumUtil.validarString(objeto.getNome(), "Nome", 'o', erros, 255);
		erros = ValidacaoComumUtil.validarString(objeto.getCpf(), "CPF", erros, 255);
		erros = ValidacaoComumUtil.validarString(objeto.getTelefone(), "Telefone", erros, 255);
		erros = ValidacaoComumUtil.validarString(objeto.getEmail(), "E-mail", erros, 255);
		erros = ValidacaoComumUtil.validarNotNull(objeto.getSexo(), "Sexo", 'o', erros);
		erros = ValidacaoComumUtil.validarNotNull(objeto.getTipo(), "Tipo do Membro", 'o', erros);
		erros = ValidacaoComumUtil.validarNotNull(objeto.getEndereco(), "Endereço", 'o', erros);
		if(objeto.getEndereco() != null) {
			erros = enderecoService.validar(objeto.getEndereco(), erros);
		}
		return erros;
	}
	
	private void posSave(Membro objeto) {
		try {
			new Thread(() -> {
				usuarioService.saveNewUserByMembro(objeto);
			}).run();
		} catch (Exception e) {
			logger.warn("posSave " + e.getMessage());
		}
	}
	
	@Override
	public Log getLogger() {
		return logger;
	}
}
