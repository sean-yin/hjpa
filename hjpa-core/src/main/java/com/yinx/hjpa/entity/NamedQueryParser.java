package com.yinx.hjpa.entity;

import javax.persistence.EntityManager;

/**
 * Created by seany on 2018/3/16.
 */
public abstract class NamedQueryParser {
    private EntityManagerProvider entityManagerProvider;

    public NamedQueryParser() {
    }

    public NamedQueryParser(EntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    public void setEntityManagerProvider(EntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    protected EntityManager getEntityManager() {
        return this.entityManagerProvider.getEntityManager();
    }

    public abstract String getQueryStringOfNamedQuery(String paramString);
}
