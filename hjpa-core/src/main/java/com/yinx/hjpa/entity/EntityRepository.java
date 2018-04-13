package com.yinx.hjpa.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * seany
 */
public abstract interface EntityRepository {
    public abstract <T extends Entity> T save(T paramT);

    public abstract void remove(Entity paramEntity);

    public abstract <T extends Entity> boolean exists(Class<T> paramClass, Serializable paramSerializable);

    public abstract <T extends Entity> T get(Class<T> paramClass, Serializable paramSerializable);

    public abstract <T extends Entity> T load(Class<T> paramClass, Serializable paramSerializable);

    public abstract <T extends Entity> T getUnmodified(Class<T> paramClass, T paramT);

    public abstract <T extends Entity> T getByBusinessKeys(Class<T> paramClass, NamedParameters paramNamedParameters);

    public abstract <T extends Entity> List<T> findAll(Class<T> paramClass);

    public abstract <T extends Entity> CriteriaQuery createCriteriaQuery(Class<T> paramClass);

    public abstract <T> List<T> find(CriteriaQuery paramCriteriaQuery);

    public abstract <T> T getSingleResult(CriteriaQuery paramCriteriaQuery);

    public abstract JpqlQuery createJpqlQuery(String paramString);

    public abstract <T> List<T> find(JpqlQuery paramJpqlQuery);

    public abstract <T> T getSingleResult(JpqlQuery paramJpqlQuery);

    public abstract int executeUpdate(JpqlQuery paramJpqlQuery);

    public abstract NamedQuery createNamedQuery(String paramString);

    public abstract <T> List<T> find(NamedQuery paramNamedQuery);

    public abstract <T> T getSingleResult(NamedQuery paramNamedQuery);

    public abstract int executeUpdate(NamedQuery paramNamedQuery);

    public abstract SqlQuery createSqlQuery(String paramString);

    public abstract <T> List<T> find(SqlQuery paramSqlQuery);

    public abstract <T> T getSingleResult(SqlQuery paramSqlQuery);

    public abstract int executeUpdate(SqlQuery paramSqlQuery);

    public abstract <T extends Entity, E extends T> List<T> findByExample(E paramE,
                                                                          ExampleSettings<T> paramExampleSettings);

    public abstract <T extends Entity> List<T> findByProperty(Class<T> paramClass, String paramString,
                                                              Object paramObject);

    public abstract <T extends Entity> List<T> findByProperties(Class<T> paramClass,
                                                                NamedParameters paramNamedParameters);

    public abstract <T extends Entity> List<T> findInSet(Class<T> paramClass, String paramString,
                                                         Set<Object> paramValues);

    public abstract <T extends Entity> List<T> findNotInSet(Class<T> paramClass, String paramString,
                                                            Set<Object> paramValues);

    public abstract String getQueryStringOfNamedQuery(String paramString);

    public abstract void flush();

    public abstract void refresh(Entity paramEntity);

    public abstract void clear();
}
