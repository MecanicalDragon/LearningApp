package net.medrag.spring.infrastructure;

import lombok.SneakyThrows;
import lombok.val;
import net.medrag.spring.infrastructure.api.ConfigurationReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
public class PropertiesConfigurationReader implements ConfigurationReader {

    private final String configuration;

    public PropertiesConfigurationReader() {
        configuration = "configuration.properties";
    }

    public PropertiesConfigurationReader(String configuration) {
        this.configuration = configuration;
    }

    @Override
    @SneakyThrows
    public Map<String, String> readConfiguration() {
        val path = ClassLoader.getSystemClassLoader().getResource(configuration).getPath();
        return new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)))).lines()
                .map(s -> s.split("=")).collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    }
}
