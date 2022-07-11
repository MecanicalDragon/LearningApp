package net.medrag.springexamples.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author Stanislav Tretyakov
 * 11.07.2022
 */
@Getter
@ToString
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "simple")
public final class SimpleConfig {
    private final Props props;
    private final String test;
}
