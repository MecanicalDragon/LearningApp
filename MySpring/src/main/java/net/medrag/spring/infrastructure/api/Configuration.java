package net.medrag.spring.infrastructure.api;

import java.util.Map;
import java.util.Set;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
public interface Configuration {
    void buildBeanDefinitions();

    Set<String> getBeanIds();

    Class getBeanClassById(String id);
}
