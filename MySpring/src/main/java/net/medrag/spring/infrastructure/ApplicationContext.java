package net.medrag.spring.infrastructure;

import net.medrag.spring.infrastructure.api.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
public class ApplicationContext {
    private Configuration configuration;
    private Map<String, Object> beans;
    private BeanInitializer initializer;

    ApplicationContext(Configuration configuration, BeanInitializer initializer) {
        this.configuration = configuration;
        this.initializer = initializer;
        beans = new HashMap<>();
    }

    public Object getBean(String id) {
        return beans.get(id);
    }

    void startContext() {
        configuration.buildBeanDefinitions();
        createBeans();
        configureBeans();
        System.out.println("CONTEXT IS STARTED!");
    }

    private void configureBeans() {
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object bean = initializer.configureBean(entry.getValue(), this);
            entry.setValue(bean);
        }
    }

    private void createBeans() {
        for (String beanId : configuration.getBeanIds()) {
            Class beanClass = configuration.getBeanClassById(beanId);
            Object bean = initializer.createBeanWithDefinition(beanClass);
            beans.put(beanId, bean);
        }
    }
}
