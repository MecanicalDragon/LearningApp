package net.medrag.spring.infrastructure.config;

import net.medrag.spring.infrastructure.api.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
public class PropertiesFileConfiguration implements net.medrag.spring.infrastructure.api.Configuration {

    private Map<String, Class> beanDefinitions;
    private ConfigurationReader configurationReader;

    public PropertiesFileConfiguration(ConfigurationReader configurationReader) {
        this.configurationReader = configurationReader;
        this.beanDefinitions = new HashMap<>();
    }

    @Override
    public void buildBeanDefinitions() {
        configurationReader.readConfiguration().forEach((k, v) -> {
            try {
                beanDefinitions.put(k, Class.forName(v));
            } catch (ClassNotFoundException e) {
                System.err.println("Hasn't been found class for name '" + e.getMessage() + "'.");
                System.err.println("Application failed to start.");
                System.exit(3);
            }
        });
    }

    @Override
    public Set<String> getBeanIds() {
        return beanDefinitions.keySet();
    }

    @Override
    public Class getBeanClassById(String id) {
        return Optional.of(beanDefinitions.get(id)).orElseThrow(() -> {
            throw new RuntimeException("No such bean in context: " + id);
        });
    }
}
