package com.yinx.hjpa.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.yinx.hjpa.ioc.InstanceFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;


/**
 * Created by seany on 2018/3/16.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CriteriaQuery {
    private final EntityRepository repository;
    private final CriterionBuilder criterionBuilder = (CriterionBuilder) InstanceFactory
            .getInstance(CriterionBuilder.class);
    private final Class<? extends Entity> entityClass;
    private int firstResult;
    private int maxResults;
    private QueryCriterion criterion = this.criterionBuilder.empty();
    private final OrderSettings orderSettings = new OrderSettings();

    public CriteriaQuery(EntityRepository repository, Class<? extends Entity> entityClass) {
        Assert.notNull(repository);
        Assert.notNull(entityClass);
        this.repository = repository;
        this.entityClass = entityClass;
    }

    public Class getEntityClass() {
        return this.entityClass;
    }

    public int getFirstResult() {
        return this.firstResult;
    }

    public CriteriaQuery setFirstResult(int firstResult) {
        this.firstResult = firstResult;
        return this;
    }

    public int getMaxResults() {
        return this.maxResults;
    }

    public CriteriaQuery setMaxResults(int maxResults) {
        this.maxResults = maxResults;
        return this;
    }

    public QueryCriterion getQueryCriterion() {
        return this.criterion;
    }

    public OrderSettings getOrderSettings() {
        return this.orderSettings;
    }

    public String getQueryString() {
        String result = String.format("select distinct(%s) from %s as %s ", "rootEntity", this.entityClass.getName(),
                "rootEntity");

        if (StringUtils.isNotEmpty(this.criterion.toQueryString())) {
            result = new StringBuilder().append(result).append(" where ").append(this.criterion.toQueryString())
                    .toString();
        }
        result = new StringBuilder().append(result).append(getOrderClause()).toString();
        return result;
    }

    private String getOrderClause() {
        List<KeyValue<String, Boolean>> orderBy = this.orderSettings.getOrderBy();
        if (orderBy.isEmpty()) {
            return "";
        }
        List elements = new ArrayList();
        for (KeyValue each : orderBy) {
            elements.add(new StringBuilder().append("rootEntity.").append((String) each.getKey())
                    .append(((Boolean) each.getValue()).booleanValue() ? " asc" : " desc").toString());
        }
        return new StringBuilder().append(" order by ").append(StringUtils.join(elements, ", ")).toString();
    }

    public NamedParameters getParameters() {
        return this.criterion.getParameters();
    }

    public CriteriaQuery eq(String propName, Object value) {
        this.criterion = this.criterion.and(this.criterionBuilder.eq(propName, value));
        return this;
    }

    public CriteriaQuery notEq(String propName, Object value) {
        this.criterion = this.criterion.and(this.criterionBuilder.notEq(propName, value));
        return this;
    }

    public CriteriaQuery gt(String propName, Comparable<?> value) {
        this.criterion = this.criterion.and(this.criterionBuilder.gt(propName, value));
        return this;
    }

    public CriteriaQuery ge(String propName, Comparable<?> value) {
        this.criterion = this.criterion.and(this.criterionBuilder.ge(propName, value));
        return this;
    }

    public CriteriaQuery lt(String propName, Comparable<?> value) {
        this.criterion = this.criterion.and(this.criterionBuilder.lt(propName, value));
        return this;
    }

    public CriteriaQuery le(String propName, Comparable<?> value) {
        this.criterion = this.criterion.and(this.criterionBuilder.le(propName, value));
        return this;
    }

    public CriteriaQuery eqProp(String propName, String otherProp) {
        this.criterion = this.criterion.and(this.criterionBuilder.eqProp(propName, otherProp));
        return this;
    }

    public CriteriaQuery notEqProp(String propName, String otherProp) {
        this.criterion = this.criterion.and(this.criterionBuilder.notEqProp(propName, otherProp));
        return this;
    }

    public CriteriaQuery gtProp(String propName, String otherProp) {
        this.criterion = this.criterion.and(this.criterionBuilder.gtProp(propName, otherProp));
        return this;
    }

    public CriteriaQuery geProp(String propName, String otherProp) {
        this.criterion = this.criterion.and(this.criterionBuilder.geProp(propName, otherProp));
        return this;
    }

    public CriteriaQuery ltProp(String propName, String otherProp) {
        this.criterion = this.criterion.and(this.criterionBuilder.ltProp(propName, otherProp));
        return this;
    }

    public CriteriaQuery leProp(String propName, String otherProp) {
        this.criterion = this.criterion.and(this.criterionBuilder.leProp(propName, otherProp));
        return this;
    }

    public CriteriaQuery sizeEq(String propName, int size) {
        this.criterion = this.criterion.and(this.criterionBuilder.sizeEq(propName, size));
        return this;
    }

    public CriteriaQuery sizeNotEq(String propName, int size) {
        this.criterion = this.criterion.and(this.criterionBuilder.sizeNotEq(propName, size));
        return this;
    }

    public CriteriaQuery sizeGt(String propName, int size) {
        this.criterion = this.criterion.and(this.criterionBuilder.sizeGt(propName, size));
        return this;
    }

    public CriteriaQuery sizeGe(String propName, int size) {
        this.criterion = this.criterion.and(this.criterionBuilder.sizeGe(propName, size));
        return this;
    }

    public CriteriaQuery sizeLt(String propName, int size) {
        this.criterion = this.criterion.and(this.criterionBuilder.sizeLt(propName, size));
        return this;
    }

    public CriteriaQuery sizeLe(String propName, int size) {
        this.criterion = this.criterion.and(this.criterionBuilder.sizeLe(propName, size));
        return this;
    }

    public CriteriaQuery containsText(String propName, String value) {
        this.criterion = this.criterion.and(this.criterionBuilder.containsText(propName, value));
        return this;
    }

    public CriteriaQuery startsWithText(String propName, String value) {
        this.criterion = this.criterion.and(this.criterionBuilder.startsWithText(propName, value));
        return this;
    }

    public CriteriaQuery in(String propName, Collection<? extends Object> value) {
        this.criterion = this.criterion.and(this.criterionBuilder.in(propName, value));
        return this;
    }

    public CriteriaQuery in(String propName, Object[] value) {
        this.criterion = this.criterion.and(this.criterionBuilder.in(propName, value));
        return this;
    }

    public CriteriaQuery notIn(String propName, Collection<? extends Object> value) {
        this.criterion = this.criterion.and(this.criterionBuilder.notIn(propName, value));
        return this;
    }

    public CriteriaQuery notIn(String propName, Object[] value) {
        this.criterion = this.criterion.and(this.criterionBuilder.notIn(propName, value));
        return this;
    }

    public <E> CriteriaQuery between(String propName, Comparable<E> from, Comparable<E> to) {
        this.criterion = this.criterion.and(this.criterionBuilder.between(propName, from, to));
        return this;
    }

    public CriteriaQuery isNull(String propName) {
        this.criterion = this.criterion.and(this.criterionBuilder.isNull(propName));
        return this;
    }

    public CriteriaQuery notNull(String propName) {
        this.criterion = this.criterion.and(this.criterionBuilder.notNull(propName));
        return this;
    }

    public CriteriaQuery isEmpty(String propName) {
        this.criterion = this.criterion.and(this.criterionBuilder.isEmpty(propName));
        return this;
    }

    public CriteriaQuery notEmpty(String propName) {
        this.criterion = this.criterion.and(this.criterionBuilder.notEmpty(propName));
        return this;
    }

    public CriteriaQuery isTrue(String propName) {
        this.criterion = this.criterion.and(this.criterionBuilder.isTrue(propName));
        return this;
    }

    public CriteriaQuery isFalse(String propName) {
        this.criterion = this.criterion.and(this.criterionBuilder.isFalse(propName));
        return this;
    }

    public CriteriaQuery isBlank(String propName) {
        this.criterion = this.criterion.and(this.criterionBuilder.isBlank(propName));
        return this;
    }

    public CriteriaQuery notBlank(String propName) {
        this.criterion = this.criterion.and(this.criterionBuilder.notBlank(propName));
        return this;
    }

    public CriteriaQuery not(QueryCriterion otherCriterion) {
        this.criterion = this.criterion.and(this.criterionBuilder.not(otherCriterion));
        return this;
    }

    public CriteriaQuery and(QueryCriterion[] queryCriterions) {
        this.criterion = this.criterion.and(this.criterionBuilder.and(queryCriterions));
        return this;
    }

    public CriteriaQuery or(QueryCriterion[] queryCriterions) {
        this.criterion = this.criterion.and(this.criterionBuilder.or(queryCriterions));
        return this;
    }

    public CriteriaQuery asc(String propName) {
        this.orderSettings.asc(propName);
        return this;
    }

    public CriteriaQuery desc(String propName) {
        this.orderSettings.desc(propName);
        return this;
    }

    public <T> List<T> list() {
        return this.repository.find(this);
    }

    public <T> T singleResult() {
        return this.repository.getSingleResult(this);
    }
}
