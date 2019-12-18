package com.moraes.igrejaservice.api.service.implementacao;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.moraes.igrejaservice.api.model.Membro;
import com.moraes.igrejaservice.api.model.Usuario;
import com.moraes.igrejaservice.api.persistencia.hql.GenericDAO;
import com.moraes.igrejaservice.api.persistencia.jpa.UsuarioDAO;
import com.moraes.igrejaservice.api.security.jwt.JwtTokenUtil;
import com.moraes.igrejaservice.api.service.PermissaoService;
import com.moraes.igrejaservice.api.service.UsuarioService;
import com.moraes.igrejaservice.api.util.FacesUtil;
import com.moraes.igrejaservice.api.util.StringUtil;

import lombok.Getter;

@Getter
@Service
public class UsuarioServiceIMPL implements UsuarioService{

	private static final Log logger = LogFactory.getLog(UsuarioService.class);
	
	@Autowired
	private UsuarioDAO persistencia;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private PermissaoService permissaoService;
	
	@Override
	public Usuario findByField(String field, Object value) {
		try {
			Usuario objeto = genericDAO.findByField(Usuario.class, field, value);
			return objeto;
		} catch (Exception e) {
			logger.warn("findByField " + e.getMessage());
			return null;
		}
	}
	
	@Override
	public Usuario save(Usuario objeto, String token) throws Exception {
		Usuario usuario = findByToken(token);
		if(!objeto.getMembro().getId().equals(usuario.getMembro().getId())) {
			throw new Exception(FacesUtil.propertiesLoader().getProperty("usuarioNotPermissao"));
		}
		else {
			return save(objeto);
		}
	}
	
	@Override
	public Usuario save(Usuario objeto) throws Exception {
		try {
			final List<Usuario> users = persistencia.findByLoginAndIdNot(objeto.getLogin(), objeto.getId() == null ? 0l : objeto.getId());
			if(users != null && !users.isEmpty()) {
				throw new Exception(FacesUtil.propertiesLoader().getProperty("usuarioLoginEmUso"));
			}
			if(StringUtil.isNotNullOrEmpity(objeto.getSenha()) && objeto.getSenha().length() <= 50) {
				objeto.setSenha(passwordEncoder.encode(objeto.getSenha()));
			}
			objeto = persistencia.save(objeto);
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
	public Usuario findByToken(String token) {
		try {
			final String login = jwtTokenUtil.getUsernameFromToken(token);
			return findByField("login", login);
		} catch (Exception e) {
			logger.error("findByToken " + e.getMessage());
			return null;
		}
	}
	
	public void createFirstUser() {
		
	}
	
	@Override
	public List<String> validar(Usuario objeto) {
		List<String> erros = new LinkedList<>();
//		erros = ValidacaoComumUtil.validarString(objeto.getNome(), "Nome", 'o', erros, 255);
//		erros = ValidacaoComumUtil.validarString(objeto.getDescricao(), "Descrição", 'a', erros, 255);
//		erros = ValidacaoComumUtil.validarNotNullAndMaiorZero(objeto.getUsuario(), "Usuario", 'o', erros);
		return erros;
	}
	
	@Override
	public Usuario saveNewUserByMembro(Membro objeto) {
		try {
			final String[] nome = objeto.getNome().split(" ");
			String login = nome[0] + (nome.length > 1 ? "-" : "");
			for (int i = 1; i < nome.length; i++) {
				if(nome[i].length() > 2) {
					login += nome[i].charAt(0);
				}
			}
			return save(new Usuario(objeto).setLogin(login.toLowerCase()).
					setPermissoes(permissaoService.getPermissoesByTipoMembro(objeto.getTipo())));
		} catch (Exception e) {
			logger.warn("saveNewUserByMembro " + e.getMessage());
		}
		return null;
	}
	
	@Override
	public Log getLogger() {
		return logger;
	}
}
