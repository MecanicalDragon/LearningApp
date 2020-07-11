package net.medrag.spring.infrastructure.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Stanislav Tretyakov
 * 10.07.2020
 */
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface Broadcast {
}
