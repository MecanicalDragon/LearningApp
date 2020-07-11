package net.medrag.spring.infrastructure.annotations;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Stanislav Tretyakov
 * 10.07.2020
 */
@Retention(RUNTIME)
public @interface ConfigurationOrder {
    int order() default 10;
}
