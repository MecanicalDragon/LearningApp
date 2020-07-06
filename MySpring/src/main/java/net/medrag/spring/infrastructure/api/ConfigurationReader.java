package net.medrag.spring.infrastructure.api;

import java.util.Map;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
public interface ConfigurationReader {
    Map<String,String> readConfiguration();
}
