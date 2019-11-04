package com.moraes.igrejaservice.api.persistencia.hql.implementacao;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.moraes.igrejaservice.api.persistencia.hql.GenericDAO;

import lombok.Getter;

@SuppressWarnings("unchecked")
@Transactional
@Repository
public class GenericDAOimpl implements GenericDAO{
	
	private static final Log logger = LogFactory.getLog(GenericDAO.class);
	
	@Getter
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public <T> T findByField(Class<?> clazz, String field, Object value) {
		try {
			final String entityName = clazz.getSimpleName();
			final String entityNick = entityName.toLowerCase();
			final String hql = String.format("SELECT %s FROM %s %s WHERE %s.%s = '%s'", 
					entityNick, entityName, entityNick, entityNick, field, value);
			T objeto = (T) entityManager.createQuery(hql, clazz).setMaxResults(1).getSingleResult();
			return objeto;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public <T> T findByFieldNative(Class<?> clazz, String field, Object value) {
		try {
			Table table = clazz.getAnnotation(javax.persistence.Table.class);
			final String entityName = table.name();
			final String entityNick = entityName.toLowerCase();
			final String hql = String.format("SELECT * FROM %s %s WHERE %s.%s = '%s'", 
					entityName, entityNick, entityNick, field, value);
			T objeto = (T) entityManager.createNativeQuery(hql, clazz).setMaxResults(1).getSingleResult();
			return objeto;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public <T> List<T> findByFieldNativeList(Class<?> clazz, String field, Object value) {
		try {
			Table table = clazz.getAnnotation(javax.persistence.Table.class);
			final String entityName = table.name();
			final String entityNick = entityName.toLowerCase();
			final String hql = String.format("SELECT * FROM %s %s WHERE %s.%s = '%s'", 
					entityName, entityNick, entityNick, field, value);
			List<T> objetos = entityManager.createNativeQuery(hql, clazz).getResultList();
			return objetos;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public Boolean existsByField(Class<?> clazz, String field, Object value) throws Exception {
		try {
			Long objeto = countByField(clazz, field, value);
			if(objeto != null && objeto > 0) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Boolean.FALSE;
		}
	}

	@Override
	public Long countByField(Class<?> clazz, String field, Object value) throws Exception {
		try {
			final String tableName = clazz.getSimpleName();
			final String sql = String.format("SELECT COUNT(x) FROM %s x WHERE x.%s = '%s'", 
					tableName, field, value);
			Long objeto = new Long(entityManager.createQuery(sql).getSingleResult() + "");
			return objeto;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new Long(0);
		}
	}
	
	@Override
	public <T> T findByIdEager(Class<T> type, String pk) {
		Table table = type.getAnnotation(javax.persistence.Table.class);
		if (table != null) {

			String hql = String.format("SELECT %s FROM %s %s", type.getSimpleName().toLowerCase(), type.getSimpleName(),
					type.getSimpleName().toLowerCase());
			//JOIN FETCH apelidoTabela.atributo apelidoAtributo
			Field[] atribustos = type.getDeclaredFields();
			StringBuilder joins = new StringBuilder("");
			StringBuilder where = new StringBuilder("");
			for(Field atributo : atribustos) {
				if(atributo.getAnnotation(ManyToOne.class) != null 
						|| atributo.getAnnotation(OneToOne.class) != null 
						|| atributo.getAnnotation(ManyToMany.class) != null 
						|| atributo.getAnnotation(OneToMany.class) != null) {
					joins.append(" JOIN FETCH ").append(type.getSimpleName().toLowerCase());
					joins.append(".").append(atributo.getName()).append(" ");
					joins.append(atributo.getName().toLowerCase());
				}else if(atributo.getAnnotation(Id.class) != null) {
					where.append(" WHERE ").append(type.getSimpleName().toLowerCase());
					where.append(".").append(atributo.getName()).append(" = '").append(pk).append("'");
				}
			}
			hql += joins.toString();
			hql += where.toString();
			return entityManager.createQuery(hql, type).setMaxResults(1).getSingleResult();

		}
		return null;
	}
	
	@Override
	public List<?> findQuery(String hql, Map<String, Object> parametros) {
		try {
			return findQueryComplementar(hql, parametros).getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public Object findQuerySingleResult(String hql, Map<String, Object> parametros) {
		try {
			return findQueryComplementar(hql, parametros).getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public <T> T findQuerySingleResult(Class<T> clazz, String hql, Map<String, Object> parametros) {
		try {
			return (T) findQueryComplementar(clazz, hql, parametros).getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public Object findNativeQuerySingleResult(String sql, Map<String, Object> parametros) {
		try {
			return findNativeQueryComplementar(sql, parametros).getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<?> findNativeQuery(String sql, Map<String, Object> parametros) {
		try {
			return findNativeQueryComplementar(sql, parametros).getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public void updateEntityFieldNative(Object id, String table, String field, Object value) {
		try {
			Map<String, Object> parametros = new HashMap<>();
			parametros.put(field, value);
			parametros.put("id", id);
			updateNativeQuery(String.format(
					"UPDATE %s SET %s = :%s WHERE id = :id", table, field, field)
					, parametros);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Boolean updateQuery(String hql, Map<String, Object> parametros) {
		try {
			Query query = getEntityManager().createQuery(hql);
			return updateQuery(query, parametros);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Boolean.FALSE;
		}
	}
	
	@Override
	public Boolean updateNativeQuery(String sql, Map<String, Object> parametros) {
		try {
			Query query = getEntityManager().createNativeQuery(sql);
			return updateQuery(query, parametros);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Boolean.FALSE;
		}
	}

	@Override
	public <T> T update(T objeto) {
		try {
			return entityManager.merge(objeto);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public void delete(Object objeto) throws Exception {
		try {
			this.entityManager.remove(objeto);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public EntityManager begin() {
		try {
			this.entityManager.getTransaction().begin();
			logger.info("Transaction Begin");
			return this.entityManager;
		} catch (Exception e) {
			this.entityManager.getTransaction().rollback();
			logger.warn("Transaction Rollback");
			logger.error(e.getMessage());
			throw e;
		}
	}
	
	private Query findQueryComplementar(String hql, Map<String, Object> parametros) {
		try {
			Query query = getEntityManager().createQuery(hql);
			if(parametros != null && !parametros.isEmpty()) {
				parametros.keySet().forEach(key -> query.setParameter(key, parametros.get(key)));
			}
			return query;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	private <T> Query findQueryComplementar(Class<T> clazz, String hql, Map<String, Object> parametros) {
		try {
			Query query = getEntityManager().createQuery(hql, clazz);
			if(parametros != null && !parametros.isEmpty()) {
				parametros.keySet().forEach(key -> query.setParameter(key, parametros.get(key)));
			}
			return query;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	

	private Boolean updateQuery(Query query, Map<String, Object> parametros) {
		try {
			if(parametros != null && !parametros.isEmpty()) {
				parametros.keySet().forEach(key -> query.setParameter(key, parametros.get(key)));
			}
			query.executeUpdate();
			return Boolean.TRUE;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Boolean.FALSE;
		}
	}
	
	private Query findNativeQueryComplementar(String sql, Map<String, Object> parametros) {
		try {
			Query query = getEntityManager().createNativeQuery(sql);
			if(parametros != null && !parametros.isEmpty()) {
				parametros.keySet().forEach(key -> query.setParameter(key, parametros.get(key)));
			}
			return query;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
