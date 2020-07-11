package net.medrag.spring.infrastructure;

import lombok.SneakyThrows;
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
 * 11.07.2020
 */
public class PropertiesInfrastructureReader implements ConfigurationReader {

    private final static String DEFAULT_INFRASTRUCTURE = "infrastructure.properties";

    private final String infrastructure;

    PropertiesInfrastructureReader() {
        infrastructure = DEFAULT_INFRASTRUCTURE;
    }

    public PropertiesInfrastructureReader(String infrastructure) {
        this.infrastructure = infrastructure == null || infrastructure.isBlank() ? DEFAULT_INFRASTRUCTURE : infrastructure;
    }

    @Override
    @SneakyThrows
    public Map<String, String> readConfiguration() {
        URL resource = ClassLoader.getSystemClassLoader().getResource(infrastructure);
        if (resource == null) throw new Exception("Cannot find specified infrastructure file.");
        return new BufferedReader(new InputStreamReader(new FileInputStream(new File(resource.getPath())))).lines()
                .collect(Collectors.toMap(s -> s, s -> s));
    }
}
