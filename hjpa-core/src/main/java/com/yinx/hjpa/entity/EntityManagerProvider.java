package com.yinx.hjpa.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.yinx.hjpa.exception.IocInstanceNotFoundException;
import com.yinx.hjpa.ioc.InstanceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by seany on 2018/3/16.
 */
public class EntityManagerProvider {
    protected final static Logger logger = LoggerFactory.getLogger(EntityManagerProvider.class);
    private EntityManagerFactory entityManagerFactory;
    private final ThreadLocal<EntityManager> entityManagerHolder = new ThreadLocal<EntityManager>();

    public EntityManagerProvider() {
        this.entityManagerFactory = (InstanceFactory.getInstance(EntityManagerFactory.class));
    }

    public EntityManagerProvider(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManagerProvider(EntityManager entityManager) {
        this.entityManagerHolder.set(entityManager);
    }

    public EntityManager getEntityManager() {
        EntityManager result = this.entityManagerHolder.get();
        if ((result != null) && (result.isOpen())) {
            return result;
        }
        result = getEntityManagerFromIoC();
        this.entityManagerHolder.set(result);
        return result;
    }

    public EntityManager getEntityManagerFromIoC() {
        try {
            return InstanceFactory.getInstance(EntityManager.class);
        } catch (IocInstanceNotFoundException e) {
            logger.error(e.getMessage(), e);
            if (this.entityManagerFactory == null)
                this.entityManagerFactory = InstanceFactory.getInstance(EntityManagerFactory.class);
        }
        return this.entityManagerFactory.createEntityManager();
    }
}
