package net.medrag.microservices.misc.jvalidation.custom;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Stanislav Tretyakov
 * 24.07.2022
 */
@Retention(RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {CustomStringValidator.class, CustomIntValidator.class})
public @interface Custom {

    String string() default "";
    int integer() default 0;

    // manadatory params
    String message() default "Doesn't conform custom constraints";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
