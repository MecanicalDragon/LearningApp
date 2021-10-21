package net.medrag.microservices.misc.bpp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Stanislav Tretyakov
 * 21.10.2021
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RUNTIME)
public @interface BppIntercepted {
    String printBefore();
    String printAfter();
}
