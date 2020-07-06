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

    public ApplicationContext(Configuration configuration, BeanInitializer initializer) {
        this.configuration = configuration;
        this.initializer = initializer;
        beans = new HashMap<>();
    }

    public Object getBean(String id) {
        return beans.get(id);
    }

    public void startContext() {
        createBeansWithDefinitions();
        configureBeans();

        System.out.println("CONTEXT IS STARTED!");
    }

    private void configureBeans() {
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            initializer.configureBean(entry.getValue(), this);
        }
    }

    private void createBeansWithDefinitions() {
        for (Map.Entry<String, Class> entry : configuration.getBeanDefinitionMap().entrySet()) {
            Object bean = initializer.createBeanWithDefinition(entry.getValue());
            beans.put(entry.getKey(), bean);
        }
    }
}
