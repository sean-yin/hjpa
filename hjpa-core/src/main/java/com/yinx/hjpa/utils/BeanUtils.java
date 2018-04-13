package com.yinx.hjpa.utils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Package : utils
 *
 * @author YixinCapital -- seany
 *         2018/4/10 11:25
 */
public class BeanUtils {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);
    private final Object bean;
    private final BeanClassUtils beanClassUtils;

    public BeanUtils(Object bean) {
        Assert.notNull(bean);
        this.bean = bean;
        this.beanClassUtils = new BeanClassUtils(bean.getClass());
    }

    public Map<String, Class<?>> getPropTypes() {
        return this.beanClassUtils.getPropTypes();
    }

    public Map<String, Object> getPropValues() {
        return this.getPropValues(this.getReadablePropNames());
    }

    public Map<String, Object> getPropValuesExclude(String[] excludePropNames) {
        return this.getPropValues(this.beanClassUtils.getReadablePropNamesExclude(excludePropNames));
    }

    public Map<String, Object> getPropValuesExclude(Class<? extends Annotation>[] excludeAnnotations) {
        return this.getPropValues(this.beanClassUtils.getReadablePropNamesExclude(excludeAnnotations));
    }

    private Map<String, Object> getPropValues(Set<String> propNames) {
        HashMap results = new HashMap();
        Map props = this.beanClassUtils.getPropertyDescriptors();

        try {
            Iterator e = propNames.iterator();

            while(e.hasNext()) {
                String propName = (String)e.next();
                PropertyDescriptor propertyDescriptor = (PropertyDescriptor)props.get(propName);
                Method readMethod = propertyDescriptor.getReadMethod();
                if(readMethod != null) {
                    Object value = readMethod.invoke(this.bean, new Object[0]);
                    results.put(propName, value);
                }
            }

            return results;
        } catch (Exception var9) {
            logger.error(var9.getMessage(), var9);
            throw new RuntimeException(var9);
        }
    }

    public Set<String> getPropNames() {
        return this.beanClassUtils.getPropNames();
    }

    public Set<String> getReadablePropNames() {
        return this.beanClassUtils.getReadablePropNames();
    }

    public Set<String> getWritablePropNames() {
        return this.beanClassUtils.getWritablePropNames();
    }

    public Object getPropValue(String propName) {
        return this.getPropValues().get(propName);
    }

    public void setPropValue(String key, Object value) {
        Iterator var3 = this.beanClassUtils.getPropertyDescriptors().entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            if(((String)entry.getKey()).equals(key)) {
                PropertyDescriptor propertyDescriptor = (PropertyDescriptor)entry.getValue();
                Method writeMethod = propertyDescriptor.getWriteMethod();
                if(writeMethod != null) {
                    try {
                        writeMethod.invoke(this.bean, new Object[]{value});
                    } catch (IllegalAccessException var8) {
                        java.util.logging.Logger.getLogger(BeanUtils.class.getName()).log(Level.SEVERE, (String)null, var8);
                    } catch (IllegalArgumentException var9) {
                        java.util.logging.Logger.getLogger(BeanUtils.class.getName()).log(Level.SEVERE, (String)null, var9);
                    } catch (InvocationTargetException var10) {
                        java.util.logging.Logger.getLogger(BeanUtils.class.getName()).log(Level.SEVERE, (String)null, var10);
                    }
                }
            }
        }

    }

    public void populate(Map<String, ? extends Object> properties) {
        Iterator var2 = properties.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            this.setPropValue((String)entry.getKey(), entry.getValue());
        }

    }

    public void copyPropertiesFrom(Object otherBean) {
        copyProperties(otherBean, this.bean, new String[0]);
    }

    public void copyPropertiesTo(Object otherBean) {
        copyProperties(this.bean, otherBean, new String[0]);
    }

    private static void copyProperties(Object fromBean, Object toBean, String[] excludeProps) {
        BeanUtils from = new BeanUtils(fromBean);
        BeanUtils to = new BeanUtils(toBean);
        Map values = from.getPropValues();
        Set propsToCopy = to.getWritablePropNames();
        if(excludeProps != null) {
            propsToCopy.removeAll(Arrays.asList(excludeProps));
        }

        Iterator var7 = propsToCopy.iterator();

        while(var7.hasNext()) {
            String prop = (String)var7.next();
            if(values.containsKey(prop)) {
                to.setPropValue(prop, values.get(prop));
            }
        }

    }

    public void copyPropertiesFrom(Object otherBean, String[] excludeProps) {
        copyProperties(otherBean, this.bean, excludeProps);
    }

    public void copyPropertiesTo(Object otherBean, String[] excludeProps) {
        copyProperties(this.bean, otherBean, excludeProps);
    }
}
