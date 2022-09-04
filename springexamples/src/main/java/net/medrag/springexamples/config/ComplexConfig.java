package net.medrag.springexamples.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
    private final InnerComplexConfig innerComplexConfig;

    public ComplexConfig(String one, String two, String[] array, InnerComplexConfig innerComplexConfig) {
        this.one = one;
        this.two = two;
        this.array = array == null ? new String[0] : array;
        this.innerComplexConfig = innerComplexConfig;
    }

    @Getter
    @ToString
    @ConstructorBinding
    @RequiredArgsConstructor
    public static final class InnerComplexConfig {
        private final String inner;
    }
}
