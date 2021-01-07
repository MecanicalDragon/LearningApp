package net.medrag.spring.infrastructure.annotations;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Stanislav Tretyakov
 * 11.07.2020
 */
@Retention(RUNTIME)
public @interface Bean {
    String id() default "";
    String scope() default "singleton";
    boolean registerByInterface() default true;
    int precedence() default 0;
}
