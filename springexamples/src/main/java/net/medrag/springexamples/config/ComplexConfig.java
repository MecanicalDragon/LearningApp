package net.medrag.springexamples.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author Stanislav Tretyakov
 * 02.08.2022
 */
@Getter
@ToString
@ConstructorBinding
@ConfigurationProperties(prefix = "simple.complex")
public final class ComplexConfig {
    private final String one;
    private final String two;
    private final String[] array;

    public ComplexConfig(String one, String two, String[] array) {
        this.one = one;
        this.two = two;
        this.array = array == null ? new String[0] : array;
    }
}
