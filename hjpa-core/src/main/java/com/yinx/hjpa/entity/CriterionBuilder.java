package com.yinx.hjpa.entity;

import java.util.Collection;

/**
 * Created by seany on 2018/3/16.
 */
public abstract interface CriterionBuilder {
    public abstract QueryCriterion eq(String paramString, Object paramObject);

    public abstract QueryCriterion notEq(String paramString, Object paramObject);

    public abstract QueryCriterion ge(String paramString, Comparable<?> paramComparable);

    public abstract QueryCriterion gt(String paramString, Comparable<?> paramComparable);

    public abstract QueryCriterion le(String paramString, Comparable<?> paramComparable);

    public abstract QueryCriterion lt(String paramString, Comparable<?> paramComparable);

    public abstract QueryCriterion eqProp(String paramString1, String paramString2);

    public abstract QueryCriterion notEqProp(String paramString1, String paramString2);

    public abstract QueryCriterion gtProp(String paramString1, String paramString2);

    public abstract QueryCriterion geProp(String paramString1, String paramString2);

    public abstract QueryCriterion ltProp(String paramString1, String paramString2);

    public abstract QueryCriterion leProp(String paramString1, String paramString2);

    public abstract QueryCriterion sizeEq(String paramString, int paramInt);

    public abstract QueryCriterion sizeNotEq(String paramString, int paramInt);

    public abstract QueryCriterion sizeGt(String paramString, int paramInt);

    public abstract QueryCriterion sizeGe(String paramString, int paramInt);

    public abstract QueryCriterion sizeLt(String paramString, int paramInt);

    public abstract QueryCriterion sizeLe(String paramString, int paramInt);

    public abstract QueryCriterion containsText(String paramString1, String paramString2);

    public abstract QueryCriterion startsWithText(String paramString1, String paramString2);

    public abstract QueryCriterion in(String paramString, Collection<?> paramCollection);

    public abstract QueryCriterion in(String paramString, Object[] paramArrayOfObject);

    public abstract QueryCriterion notIn(String paramString, Collection<?> paramCollection);

    public abstract QueryCriterion notIn(String paramString, Object[] paramArrayOfObject);

    public abstract QueryCriterion between(String paramString, Comparable<?> paramComparable1,
                                           Comparable<?> paramComparable2);

    public abstract QueryCriterion isNull(String paramString);

    public abstract QueryCriterion notNull(String paramString);

    public abstract QueryCriterion isEmpty(String paramString);

    public abstract QueryCriterion notEmpty(String paramString);

    public abstract QueryCriterion not(QueryCriterion paramQueryCriterion);

    public abstract QueryCriterion and(QueryCriterion[] paramArrayOfQueryCriterion);

    public abstract QueryCriterion or(QueryCriterion[] paramArrayOfQueryCriterion);

    public abstract QueryCriterion isTrue(String paramString);

    public abstract QueryCriterion isFalse(String paramString);

    public abstract QueryCriterion isBlank(String paramString);

    public abstract QueryCriterion notBlank(String paramString);

    public abstract QueryCriterion empty();
}
