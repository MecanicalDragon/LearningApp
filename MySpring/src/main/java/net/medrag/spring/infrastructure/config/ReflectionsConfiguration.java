package net.medrag.spring.infrastructure.config;

import net.medrag.spring.infrastructure.api.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Stanislav Tretyakov
 * 20.07.2020
 */
public class ReflectionsConfiguration implements Configuration {

    private Map<String, Class> beanDefinitions;
    private PackageReader packageReader;

    public ReflectionsConfiguration(PackageReader packageReader) {
        this.packageReader = packageReader;
        this.beanDefinitions = new HashMap<>();
    }

    @Override
    public void buildBeanDefinitions() {
        packageReader.readConfiguration().forEach((k, v) -> {
            try {
                beanDefinitions.put(k, Class.forName(v.getName()));
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
