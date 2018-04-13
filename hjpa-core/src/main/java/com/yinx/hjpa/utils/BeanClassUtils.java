package com.yinx.hjpa.utils;

/**
 * Created by seany on 2018/3/16.
 */

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BeanClassUtils {

    private final static Logger logger = LoggerFactory.getLogger(BeanClassUtils.class);

    private final Class<?> clazz;

    public BeanClassUtils(Class<?> clazz) {
        Assert.notNull(clazz);
        this.clazz = clazz;
    }

    public Map<String, Class<?>> getPropTypes() {
        Map results = new HashMap();
        for (Map.Entry each : getPropertyDescriptors().entrySet()) {
            results.put(each.getKey(), ((PropertyDescriptor) each.getValue()).getPropertyType());
        }
        return results;
    }

    public Set<String> getPropNames() {
        return getPropertyDescriptors().keySet();
    }

    public Set<String> getReadablePropNames() {
        Set results = new HashSet();
        for (Map.Entry each : getPropertyDescriptors().entrySet()) {
            if (((PropertyDescriptor) each.getValue()).getReadMethod() == null) {
                continue;
            }
            results.add(each.getKey());
        }
        return results;
    }

    public Set<String> getWritablePropNames() {
        Set results = new HashSet();
        for (Map.Entry each : getPropertyDescriptors().entrySet()) {
            if (((PropertyDescriptor) each.getValue()).getWriteMethod() == null) {
                continue;
            }
            results.add(each.getKey());
        }
        return results;
    }

    public Set<String> getReadablePropNamesExclude(String[] excludePropNames) {
        List propNamesExclude = Arrays.asList(excludePropNames);
        Set results = new HashSet();
        for (String propName : getReadablePropNames()) {
            if (propNamesExclude.contains(propName)) {
                continue;
            }
            results.add(propName);
        }
        return results;
    }

    public Set<String> getReadablePropNamesExclude(Class<? extends Annotation>[] excludeAnnotations) {
        List annotationsExclude = Arrays.asList(excludeAnnotations);
        Set results = new HashSet();
        Map props = getPropertyDescriptors();
        for (String propName : getReadablePropNames()) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) props.get(propName);
            Method readMethod = propertyDescriptor.getReadMethod();
            if ((readMethod == null) || (methodContainsAnnotation(readMethod, annotationsExclude))) {
                continue;
            }
            results.add(propName);
        }
        return results;
    }

    Map<String, PropertyDescriptor> getPropertyDescriptors() {
        Map results = new HashMap();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(this.clazz);
            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                results.put(propertyDescriptor.getName(), propertyDescriptor);
            }
            results.remove("class");
        } catch (IntrospectionException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return results;
    }

    private boolean methodContainsAnnotation(Method readMethod, List<Class<? extends Annotation>> annotationsExclude) {
        for (Class annotationClass : annotationsExclude) {
            if (readMethod.isAnnotationPresent(annotationClass)) {
                return true;
            }
        }
        return false;
    }
}
