package com.yinx.hjpa.ioc;

import java.lang.annotation.Annotation;
import java.util.*;

import javax.inject.Named;

import com.yinx.hjpa.exception.IocInstanceNotFoundException;
import com.yinx.hjpa.exception.IocInstanceNotUniqueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by seany on 2018/3/16.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class InstanceFactory {
    protected final static Logger logger = LoggerFactory.getLogger(InstanceFactory.class);
    private static InstanceProvider instanceProvider;
    private static final Map<Object, Object> instances = new HashMap();

    public static void setInstanceProvider(InstanceProvider provider) {
        instanceProvider = provider;
    }

    public static <T> T getInstance(Class<T> beanType) {
        Object result = null;
        if (null == instanceProvider) {
            setInstanceProvider(new SpringInstanceProvider(SpringContextUtil.getApplicationContext()));
        }
        if (instanceProvider != null) {
            result = getInstanceFromProvider(beanType);
        }
        if (result != null) {
            return (T) result;
        }
        result = getInstanceFromServiceLoader(beanType);
        if (result != null) {
            return (T) result;
        }
        result = instances.get(beanType);
        if (result != null) {
            return (T) result;
        }
        throw new IocInstanceNotFoundException("There's not bean of type '" + beanType + "' exists in IoC container!");
    }

    private static <T> T getInstanceFromProvider(Class<T> beanType) {
        try {
            return instanceProvider.getInstance(beanType);
        } catch (IocInstanceNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private static <T> T getInstanceFromServiceLoader(Class<T> beanType) {
        Set results = new HashSet();
        for (Iterator i$ = ServiceLoader.load(beanType).iterator(); i$.hasNext();) {
            Object instance = i$.next();
            results.add(instance);
        }
        if (results.size() > 1) {
            throw new IocInstanceNotUniqueException("There're more than one bean of type '" + beanType + "'");
        }
        if (results.size() == 1) {
            return (T) results.iterator().next();
        }
        return null;
    }

    public static <T> T getInstance(Class<T> beanType, String beanName) {
        Object result = null;
        if (instanceProvider != null) {
            result = getInstanceFromProvider(beanType, beanName);
        }
        if (result != null) {
            return (T) result;
        }
        result = getInstanceFromServiceLoader(beanType, beanName);
        if (result != null) {
            return (T) result;
        }
        result = instances.get(toName(beanType, beanName));
        if (result != null) {
            return (T) result;
        }
        throw new IocInstanceNotFoundException("There's not bean of type '" + beanType + "' exists in IoC container!");
    }

    private static <T> T getInstanceFromProvider(Class<T> beanType, String beanName) {
        try {
            return instanceProvider.getInstance(beanType, beanName);
        } catch (IocInstanceNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private static <T> T getInstanceFromServiceLoader(Class<T> beanType, String beanName) {
        Set results = new HashSet();
        for (Iterator i$ = ServiceLoader.load(beanType).iterator(); i$.hasNext();) {
            Object instance = i$.next();
            Named named = (Named) instance.getClass().getAnnotation(Named.class);
            if ((named != null) && (beanName.equals(named.value()))) {
                results.add(instance);
            }
        }
        if (results.size() > 1) {
            throw new IocInstanceNotUniqueException("There're more than one bean of type '" + beanType
                    + "' and named '" + beanName + "'");
        }

        if (results.size() == 1) {
            return (T) results.iterator().next();
        }
        return null;
    }

    public static <T> T getInstance(Class<T> beanType, Class<? extends Annotation> annotationType) {
        Object result = null;
        if (instanceProvider != null) {
            result = getInstanceFromProvider(beanType, annotationType);
        }
        if (result != null) {
            return (T) result;
        }
        result = getInstanceFromServiceLoader(beanType, annotationType);
        if (result != null) {
            return (T) result;
        }
        result = instances.get(toName(beanType, annotationType));
        if (result != null) {
            return (T) result;
        }
        throw new IocInstanceNotFoundException("There's not bean '" + annotationType + "' of type '" + beanType
                + "' exists in IoC container!");
    }

    private static <T> T getInstanceFromProvider(Class<T> beanType, Class<? extends Annotation> annotationType) {
        try {
            return instanceProvider.getInstance(beanType, annotationType);
        } catch (IocInstanceNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private static <T> T getInstanceFromServiceLoader(Class<T> beanType, Class<? extends Annotation> annotationType) {
        Set results = new HashSet();
        for (Iterator i$ = ServiceLoader.load(beanType).iterator(); i$.hasNext();) {
            Object instance = i$.next();
            Annotation beanAnnotation = instance.getClass().getAnnotation(annotationType);
            if ((beanAnnotation != null) && (beanAnnotation.annotationType().equals(annotationType))) {
                results.add(instance);
            }
        }
        if (results.size() > 1) {
            throw new IocInstanceNotUniqueException("There're more than one bean of type '" + beanType
                    + "' and annotated with '" + annotationType + "'");
        }

        if (results.size() == 1) {
            return (T) results.iterator().next();
        }
        return null;
    }

    public static <T> void bind(Class<T> serviceInterface, T serviceImplementation) {
        instances.put(serviceInterface, serviceImplementation);
    }

    public static <T> void bind(Class<T> serviceInterface, T serviceImplementation, String beanName) {
        instances.put(toName(serviceInterface, beanName), serviceImplementation);
    }

    public static void clear() {
        instances.clear();
    }

    public static <T> void bind(Class<T> serviceInterface, T serviceImplementation,
            Class<? extends Annotation> annotationType) {
        instances.put(toName(serviceInterface, annotationType), serviceImplementation);
    }

    private static String toName(Class<?> beanType, String beanName) {
        return beanType.getName() + ":" + beanName;
    }

    private static String toName(Class<?> beanType, Class<? extends Annotation> annotationType) {
        return beanType.getName() + ":" + annotationType.getName();
    }
}
