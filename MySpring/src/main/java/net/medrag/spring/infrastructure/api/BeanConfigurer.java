package net.medrag.spring.infrastructure.api;

import net.medrag.spring.infrastructure.ApplicationContext;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
public interface BeanConfigurer {
    void configure(Object bean, ApplicationContext context);
}
