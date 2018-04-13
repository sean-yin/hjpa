package com.yinx.hjpa.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.yinx.hjpa.ioc.InstanceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * EntityRepository 提供实体的数据库操作 Created by seany on 2018/3/11.
 */
@Repository("repository")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EntityJpaRepository implements EntityRepository {

	protected final static Logger logger = LoggerFactory.getLogger(EntityJpaRepository.class);

	@PersistenceContext
	private EntityManager entityManager;

	private NamedQueryParser namedQueryParser;

	public EntityJpaRepository() {
		super();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public NamedQueryParser getNamedQueryParser() {
		if (namedQueryParser == null) {
			namedQueryParser = InstanceFactory.getInstance(NamedQueryParser.class);
		}
		return namedQueryParser;
	}

	public void setNamedQueryParser(NamedQueryParser namedQueryParser) {
		this.namedQueryParser = namedQueryParser;
	}

	@Override
	@Transactional
	public <T extends Entity> T save(T entity) {
		if (existed(entity) == false || entity.notExisted()) {
			getEntityManager().persist(entity);
			logger.info("create a entity: " + entity.getClass() + "/" + entity.getId() + ".");
			return entity;
		} else {
			logger.info("update a entity: " + entity.getClass() + "/" + entity.getId() + ".");
			return getEntityManager().merge(entity);
		}
	}

	public boolean existed(Entity entity) {
		Object id = entity.getId();
		if (id == null) {
			return false;
		}
		if (id instanceof Number && ((Number) id).intValue() == 0) {
			return false;
		}
		return true;
	}

	@Transactional
	public void remove(Entity entity) {
		getEntityManager().remove(get(entity.getClass(), entity.getId()));
		logger.info("remove a entity: " + entity.getClass() + "/" + entity.getId() + ".");
	}

	public <T extends Entity> boolean exists(final Class<T> clazz, final Serializable id) {
		T entity = getEntityManager().find(clazz, id);
		return entity != null;
	}

	public <T extends Entity> T get(final Class<T> clazz, final Serializable id) {
		return getEntityManager().find(clazz, id);
	}

	public <T extends Entity> T load(final Class<T> clazz, final Serializable id) {
		return getEntityManager().getReference(clazz, id);
	}

	public <T extends Entity> T getUnmodified(final Class<T> clazz, final T entity) {
		getEntityManager().detach(entity);
		return get(clazz, entity.getId());
	}

	public <T extends Entity> T getByBusinessKeys(Class<T> clazz, NamedParameters keyValues) {
		List<T> results = findByProperties(clazz, keyValues);
		return results.isEmpty() ? null : results.get(0);
	}

	public <T extends Entity> List<T> findAll(final Class<T> clazz) {
		String queryString = "select o from " + clazz.getName() + " as o";
		return getEntityManager().createQuery(queryString).getResultList();
	}

	public <T extends Entity> CriteriaQuery createCriteriaQuery(Class<T> entityClass) {
		return new CriteriaQuery(this, entityClass);
	}

	public <T> List<T> find(CriteriaQuery dddQuery) {
		Query query = getEntityManager().createQuery(dddQuery.getQueryString());
		Map<String, Object> params = dddQuery.getParameters().getParams();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		query.setFirstResult(dddQuery.getFirstResult());
		if (dddQuery.getMaxResults() > 0) {
			query.setMaxResults(dddQuery.getMaxResults());
		}
		return query.getResultList();
	}

	public <T> T getSingleResult(CriteriaQuery dddQuery) {
		List<T> results = find(dddQuery);
		return results.isEmpty() ? null : results.get(0);
	}

	public JpqlQuery createJpqlQuery(String jpql) {
		return new JpqlQuery(this, jpql);
	}

	public <T> List<T> find(JpqlQuery jpqlQuery) {
		return getQuery(jpqlQuery).getResultList();
	}

	public <T> T getSingleResult(JpqlQuery jpqlQuery) {
		List<T> results = getQuery(jpqlQuery).getResultList();
		if (results.isEmpty()) {
			return null;
		} else {
			return results.get(0);
		}
	}

	@Transactional
	public int executeUpdate(JpqlQuery jpqlQuery) {
		return getQuery(jpqlQuery).executeUpdate();

	}

	private Query getQuery(JpqlQuery jpqlQuery) {
		Query query = getEntityManager().createQuery(jpqlQuery.getJpql());
		processQuery(query, jpqlQuery);
		return query;
	}

	public NamedQuery createNamedQuery(String queryName) {
		return new NamedQuery(this, queryName);
	}

	public <T> List<T> find(NamedQuery namedQuery) {
		return getQuery(namedQuery).getResultList();
	}

	public <T> T getSingleResult(NamedQuery namedQuery) {
		try {
			return (T) getQuery(namedQuery).getSingleResult();
		} catch (NoResultException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Transactional
	public int executeUpdate(NamedQuery namedQuery) {
		return getQuery(namedQuery).executeUpdate();
	}

	private Query getQuery(NamedQuery namedQuery) {
		Query query = getEntityManager().createNamedQuery(namedQuery.getQueryName());
		processQuery(query, namedQuery);
		return query;
	}

	public SqlQuery createSqlQuery(String sql) {
		return new SqlQuery(this, sql);
	}

	public <T> List<T> find(SqlQuery sqlQuery) {
		return getQuery(sqlQuery).getResultList();
	}

	public <T> T getSingleResult(SqlQuery sqlQuery) {
		return (T) getQuery(sqlQuery).getSingleResult();
	}

	@Transactional
	public int executeUpdate(SqlQuery sqlQuery) {
		return getQuery(sqlQuery).executeUpdate();
	}

	private Query getQuery(SqlQuery sqlQuery) {
		Query query;
		if (sqlQuery.getResultEntityClass() == null) {
			query = getEntityManager().createNativeQuery(sqlQuery.getSql());
		} else {
			query = getEntityManager().createNativeQuery(sqlQuery.getSql(),
					sqlQuery.getResultEntityClass());
		}
		processQuery(query, sqlQuery);
		Class resultEntityClass = sqlQuery.getResultEntityClass();
		return query;
	}

	public <T extends Entity, E extends T> List<T> findByExample(final E example,
			final ExampleSettings<T> settings) {
		throw new RuntimeException("not implemented yet!");
	}

	public <T extends Entity> List<T> findByProperty(Class<T> clazz, String propertyName,
			Object propertyValue) {
		return find(new CriteriaQuery(this, clazz).eq(propertyName, propertyValue));
	}

	public <T extends Entity> List<T> findByProperties(Class<T> clazz, NamedParameters properties) {
		CriteriaQuery criteriaQuery = new CriteriaQuery(this, clazz);
		for (Map.Entry<String, Object> each : properties.getParams().entrySet()) {
			criteriaQuery = criteriaQuery.eq(each.getKey(), each.getValue());
		}
		return find(criteriaQuery);
	}

	public String getQueryStringOfNamedQuery(String queryName) {
		return getNamedQueryParser().getQueryStringOfNamedQuery(queryName);
	}

	public void flush() {
		getEntityManager().flush();
	}

	public void refresh(Entity entity) {
		getEntityManager().refresh(entity);
	}

	public void clear() {
		getEntityManager().clear();
	}

	private void processQuery(Query query, BaseQuery originQuery) {
		fillParameters(query, originQuery.getParameters());
		query.setFirstResult(originQuery.getFirstResult());
		if (originQuery.getMaxResults() > 0) {
			query.setMaxResults(originQuery.getMaxResults());
		}
	}

	private void fillParameters(Query query, QueryParameters params) {
		if (params == null) {
			return;
		}
		if (params instanceof PositionalParameters) {
			fillParameters(query, (PositionalParameters) params);
		} else if (params instanceof NamedParameters) {
			fillParameters(query, (NamedParameters) params);
		} else {
			throw new UnsupportedOperationException("不支持的参数形式");
		}
	}

	private void fillParameters(Query query, PositionalParameters params) {
		Object[] paramArray = params.getParams();
		for (int i = 0; i < paramArray.length; i++) {
			query = query.setParameter(i + 1, paramArray[i]);
		}
	}

	private void fillParameters(Query query, NamedParameters params) {
		for (Map.Entry<String, Object> each : params.getParams().entrySet()) {
			query = query.setParameter(each.getKey(), each.getValue());
		}
	}

	@Override
	public <T extends Entity> List<T> findInSet(Class<T> clazz, String propertyName,
			Set<Object> paramValues) {
		CriteriaQuery criteriaQuery = new CriteriaQuery(this, clazz);
		criteriaQuery.in(propertyName, paramValues);
		return find(criteriaQuery);
	}

	@Override
	public <T extends Entity> List<T> findNotInSet(Class<T> clazz, String propertyName,
			Set<Object> paramValues) {
		CriteriaQuery criteriaQuery = new CriteriaQuery(this, clazz);
		criteriaQuery.notIn(propertyName, paramValues);
		return find(criteriaQuery);
	}

}
