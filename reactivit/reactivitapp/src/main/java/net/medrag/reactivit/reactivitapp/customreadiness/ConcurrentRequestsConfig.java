package net.medrag.reactivit.reactivitapp.customreadiness;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Stanislav Tretyakov
 * 31.08.2022
 */
@Configuration
@EnableConfigurationProperties(ConcurrentRequestsProperties.class)
public class ConcurrentRequestsConfig {
}
