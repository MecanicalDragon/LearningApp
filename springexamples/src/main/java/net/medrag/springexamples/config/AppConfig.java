package net.medrag.springexamples.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Stanislav Tretyakov
 * 11.07.2022
 */
@Configuration
@EnableConfigurationProperties(SimpleConfig.class)
public class AppConfig {
}
