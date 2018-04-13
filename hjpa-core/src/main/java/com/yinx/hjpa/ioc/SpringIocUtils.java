package com.yinx.hjpa.ioc;

/**
 * Created by seany on 2018/3/16.
 */
public class SpringIocUtils {
    private SpringIocUtils() {
    }

    private static final ThreadLocal<SpringInstanceProvider> providerHolder = new ThreadLocal();

    public static void initInstanceProvider(Class<?>[] annotatedClasses) {
        InstanceFactory.setInstanceProvider(getInstanceProvider(annotatedClasses));
    }

    private static InstanceProvider getInstanceProvider(Class<?>[] annotatedClasses) {
        SpringInstanceProvider result = (SpringInstanceProvider) providerHolder.get();
        if (result != null) {
            return result;
        }
        synchronized (SpringIocUtils.class) {
            result = new SpringInstanceProvider(annotatedClasses);
            providerHolder.set(result);
            return result;
        }
    }

    public static void initInstanceProvider(String[] acFiles) {
        InstanceFactory.setInstanceProvider(getInstanceProvider(acFiles));
    }

    private static InstanceProvider getInstanceProvider(String[] acFiles) {
        SpringInstanceProvider result = (SpringInstanceProvider) providerHolder.get();
        if (result != null) {
            return result;
        }
        synchronized (SpringIocUtils.class) {
            result = new SpringInstanceProvider(acFiles);
            providerHolder.set(result);
            return result;
        }
    }
}
