package net.medrag.spring.infrastructure.api;

import net.medrag.spring.infrastructure.ApplicationContext;

/**
 * @author Stanislav Tretyakov
 * 10.07.2020
 */
public interface ProxyConfigurer {
    <T> T createProxy(T bean, Class<? extends T> beanClass);
}
