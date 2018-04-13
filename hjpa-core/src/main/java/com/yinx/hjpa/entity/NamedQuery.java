package com.yinx.hjpa.entity;

import java.util.List;

/**
 * Created by seany on 2018/3/16.
 */
public class NamedQuery extends BaseQuery<NamedQuery> {
    private final String queryName;

    public NamedQuery(EntityRepository repository, String queryName) {
        super(repository);
        this.queryName = queryName;
    }

    public String getQueryName() {
        return this.queryName;
    }

    public <T> List<T> list() {
        return getRepository().find(this);
    }

    public <T> T singleResult() {
        return getRepository().getSingleResult(this);
    }

    public int executeUpdate() {
        return getRepository().executeUpdate(this);
    }
}
