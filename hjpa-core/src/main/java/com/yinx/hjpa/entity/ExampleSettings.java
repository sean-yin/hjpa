package com.yinx.hjpa.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by seany on 2018/3/16.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ExampleSettings<T> {
    private Class<T> entityClass;
    private final Set<String> excludedProperties = new HashSet();
    private boolean likeEnabled = false;
    private boolean ignoreCaseEnabled = false;
    private boolean excludeNone = false;
    private boolean excludeZeroes = false;

    public static <T extends Entity> ExampleSettings<T> create(Class<T> entityClass) {
        return new ExampleSettings(entityClass);
    }

    private ExampleSettings(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Class<T> getEntityClass() {
        return this.entityClass;
    }

    public Set<String> getExcludedProperties() {
        return this.excludedProperties;
    }

    public boolean isLikeEnabled() {
        return this.likeEnabled;
    }

    public boolean isIgnoreCaseEnabled() {
        return this.ignoreCaseEnabled;
    }

    public boolean isExcludeNone() {
        return this.excludeNone;
    }

    public boolean isExcludeZeroes() {
        return this.excludeZeroes;
    }

    public ExampleSettings<T> enableLike() {
        this.likeEnabled = true;
        return this;
    }

    public ExampleSettings<T> ignoreCase() {
        this.ignoreCaseEnabled = true;
        return this;
    }

    public ExampleSettings<T> excludeNone() {
        this.excludeNone = true;
        return this;
    }

    public ExampleSettings<T> excludeZeroes() {
        this.excludeZeroes = true;
        return this;
    }

    public ExampleSettings<T> exclude(String propName) {
        this.excludedProperties.add(propName);
        return this;
    }
}
