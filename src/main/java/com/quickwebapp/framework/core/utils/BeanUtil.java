/**
 * 
 */
package com.quickwebapp.framework.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

/**
 * @author 袁进勇
 *
 */
public final class BeanUtil {
    private static final Logger LOG = LoggerFactory.getLogger(BeanUtil.class);
    private static ApplicationContext applicationContext = null;

    private BeanUtil() {

    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        BeanUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return applicationContext.getBean(beanName, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return getBean(requiredType, true);
    }

    public static <T> T getBean(Class<T> requiredType, boolean throwException) {
        try {
            return applicationContext.getBean(requiredType);
        } catch (NoSuchBeanDefinitionException e) {
            if (throwException) {
                throw e;
            }

            LOG.debug("Java Bean未定义：{}。", e.getMessage());
            return null;
        }
    }
}
