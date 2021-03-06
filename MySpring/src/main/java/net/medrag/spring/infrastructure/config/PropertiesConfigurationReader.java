package net.medrag.spring.infrastructure.config;

import lombok.SneakyThrows;
import net.medrag.spring.infrastructure.api.BeanDefinition;
import net.medrag.spring.infrastructure.api.ConfigurationReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
public class PropertiesConfigurationReader implements ConfigurationReader {

    private final static String DEFAULT_CONFIGURATION = "configuration.properties";

    private final String configuration;

    public PropertiesConfigurationReader() {
        configuration = DEFAULT_CONFIGURATION;
    }

    public PropertiesConfigurationReader(String configuration) {
        this.configuration = configuration == null || configuration.isBlank() ? DEFAULT_CONFIGURATION : configuration;
    }

    @Override
    @SneakyThrows
    public Map<String, BeanDefinition> readConfiguration() {
        URL resource = ClassLoader.getSystemClassLoader().getResource(configuration);
        if (resource == null) throw new Exception("Cannot find specified configuration file.");
        return new BufferedReader(new InputStreamReader(new FileInputStream(new File(resource.getPath())))).lines()
                .map(s -> s.split("=")).collect(Collectors.toMap(arr -> arr[0], arr -> new BasicBeanDefinitionImpl(arr[1])));
    }
}
