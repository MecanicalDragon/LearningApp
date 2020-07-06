package net.medrag.spring.infrastructure;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
public class DefaultConfiguration implements net.medrag.spring.infrastructure.api.Configuration {

    @Getter
    private Map<String, Class> beansDefinitions;

    public DefaultConfiguration(Map<String, String> config) {
        this.beansDefinitions = new HashMap<>();
        config.forEach((k, v) -> {
            try {
                beansDefinitions.put(k, Class.forName(v));
            } catch (ClassNotFoundException e) {
                System.err.println("Hasn't been found class for name '" + e.getMessage() + "'.");
                System.err.println("Application failed to start.");
                System.exit(3);
            }
        });
    }

    @Override
    public Map<String, Class> getBeanDefinitionMap(){
        return beansDefinitions;
    }

    @Override
    public Class getBeanDefinition(String definition) {
        return Optional.of(beansDefinitions.get(definition)).orElseThrow(() -> {
            throw new RuntimeException("No such bean in context: " + definition);
        });
    }
}
