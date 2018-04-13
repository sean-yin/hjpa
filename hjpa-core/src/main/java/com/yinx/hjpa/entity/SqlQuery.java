package com.yinx.hjpa.entity;

import java.util.List;

/**
 * Created by seany on 2018/3/16.
 */
public class SqlQuery extends BaseQuery<SqlQuery> {
    private final String sql;
    private Class<? extends Entity> resultEntityClass;

    public SqlQuery(EntityRepository repository, String sql) {
        super(repository);
        this.sql = sql;
    }

    public String getSql() {
        return this.sql;
    }

    public Class<? extends Entity> getResultEntityClass() {
        return this.resultEntityClass;
    }

    public SqlQuery setResultEntityClass(Class<? extends Entity> resultEntityClass) {
        this.resultEntityClass = resultEntityClass;
        return this;
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
