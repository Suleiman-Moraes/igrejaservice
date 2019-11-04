package com.moraes.igrejaservice.api.persistencia.hql;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

public interface GenericDAO {
	<T> T findByField(Class<?> clazz, String field, Object value);
	
	Boolean existsByField(Class<?> clazz, String field, Object value)throws Exception;
	
	Long countByField(Class<?> clazz, String field, Object value)throws Exception;
	
	<T> T findByIdEager(Class<T> type, String pk);
	
	<T> T update(T objeto);
	
	void delete(Object objeto) throws Exception;
	
	EntityManager getEntityManager();
	
	EntityManager begin();

	Boolean updateQuery(String hql, Map<String, Object> parametros);

	Boolean updateNativeQuery(String sql, Map<String, Object> parametros);

	List<?> findQuery(String hql, Map<String, Object> parametros);

	Object findQuerySingleResult(String hql, Map<String, Object> parametros);

	Object findNativeQuerySingleResult(String sql, Map<String, Object> parametros);

	List<?> findNativeQuery(String sql, Map<String, Object> parametros);

	<T> T findQuerySingleResult(Class<T> clazz, String hql, Map<String, Object> parametros);

	void updateEntityFieldNative(Object id, String table, String field, Object value);

	<T> T findByFieldNative(Class<?> clazz, String field, Object value);

	<T> List<T> findByFieldNativeList(Class<?> clazz, String field, Object value);
}
