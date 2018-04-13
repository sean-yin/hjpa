package com.yinx.hjpa.entity;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class BaseQuery<E extends BaseQuery> {
    private final EntityRepository repository;
    private QueryParameters parameters = PositionalParameters.create();
    private final NamedParameters mapParameters = NamedParameters.create();
    private int firstResult;
    private int maxResults;

    public BaseQuery(EntityRepository repository) {
        Assert.notNull(repository);
        this.repository = repository;
    }

    public QueryParameters getParameters() {
        return this.parameters;
    }

    public E setParameters(Object[] parameters) {
        this.parameters = PositionalParameters.create(parameters);
        return (E) this;
    }

    public E setParameters(List<Object> parameters) {
        this.parameters = PositionalParameters.create(parameters);
        return (E) this;
    }

    public E setParameters(Map<String, Object> parameters) {
        this.parameters = NamedParameters.create(parameters);
        return (E) this;
    }

    public E addParameter(String key, Object value) {
        this.mapParameters.add(key, value);
        this.parameters = this.mapParameters;
        return (E) this;
    }

    public E setParameters(QueryParameters parameters) {
        this.parameters = parameters;
        return (E) this;
    }

    public int getFirstResult() {
        return this.firstResult;
    }

    public E setFirstResult(int firstResult) {
        Assert.isTrue(firstResult >= 0);
        this.firstResult = firstResult;
        return (E) this;
    }

    public int getMaxResults() {
        return this.maxResults;
    }

    public E setMaxResults(int maxResults) {
        Assert.isTrue(maxResults > 0);
        this.maxResults = maxResults;
        return (E) this;
    }

    public abstract <T> List<T> list();

    public abstract <T> T singleResult();

    public abstract int executeUpdate();

    protected EntityRepository getRepository() {
        return this.repository;
    }
}
