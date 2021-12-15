package net.medrag.microservices.aspectj.tracked;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Stanislav Tretyakov
 * 15.12.2021
 */
@Retention(RUNTIME)
@Target(ElementType.FIELD)
public @interface Tracked {
    boolean tracked() default true;
    boolean permitted() default false;
}
