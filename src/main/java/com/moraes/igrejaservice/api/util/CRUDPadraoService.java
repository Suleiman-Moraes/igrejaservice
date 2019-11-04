package com.moraes.igrejaservice.api.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpHeaders;

import com.moraes.igrejaservice.api.model.interfaces.ICrudPadraoService;
import com.moraes.igrejaservice.api.persistencia.hql.GenericDAO;
import com.moraes.igrejaservice.api.response.Response;

public interface CRUDPadraoService<T> extends ICrudPadraoService<T>{
	
	GenericDAO getGenericDAO();
	
	JpaRepository<T, Long> getPersistencia();
	
	Log getLogger();
	
	@Override
	default List<T> findAll() {
		try {
			List<T> objetos = getPersistencia().findAll();
			getLogger().info("findAll " + objetos.size());
			return objetos;
		} catch (Exception e) {
			getLogger().warn("findAll " + e.getMessage());
			return null;
		}
	}

	@Override
	default T findById(Object id) {
		try {
			T objeto = getPersistencia().findById(Long.valueOf(id.toString())).get();
			getLogger().info("findById " + id);
			return objeto;
		} catch (Exception e) {
			getLogger().warn("findById " + e.getMessage());
			return null;
		}
	}

	@Override
	default T save(T objeto) throws Exception {
		try {
			objeto = getGenericDAO().update(objeto);
			return objeto;
		} catch (DataIntegrityViolationException e) {
			getLogger().error("save " + e.getMessage());
			throw new Exception(FacesUtil.propertiesLoader().getProperty("dadosInvalidos"));
		} catch (Exception e) {
			getLogger().error("save " + e.getMessage());
			throw e;
		}
	}

	@Override
	default Boolean deleteById(String id) {
		getLogger().warn("Não pode ser excluído");
		return Boolean.FALSE;
	}

	@Override
	default Boolean existsByField(String fieldName, Object value) throws Exception {
		getLogger().error("existsByField Método não Implementado");
		return Boolean.FALSE;
	}

	@Override
	default T findByField(String field, Object value) {
		getLogger().error("findByField Método não Implementado");
		return null;
	}
	
	@Override
	default Page<T> paginarComParemetros(Integer page, Integer count) {
		try {
			Page<T> pagina = getPersistencia().findAll(PageRequest.of(page, count, Sort.by(Sort.Direction.DESC, "id")));
			getLogger().info(String.format("paginarComParemetros(%s, %s) == pagina.getContent().size() = ", 
					page, count, pagina.getContent().size()));
			return pagina;
		} catch (Exception e) {
			getLogger().warn("paginarComParemetros" + e.getMessage());
			return null;
		}
	}
	
	default HttpHeaders getHeadersComToken(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		return headers;
	}

	default void validarRespose(Response<?> response) throws Exception {
		if(response.getErros() != null && !response.getErros().isEmpty()) {
			throw new Exception(response.getErros().get(0));
		}
	}
}
