package net.medrag.microservices.misc.jvalidation.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Stanislav Tretyakov
 * 24.07.2022
 */
public class CustomIntValidator implements ConstraintValidator<Custom, Integer> {

    private int divider;

    @Override
    public void initialize(Custom constraintAnnotation) {
        divider = constraintAnnotation.integer();
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return integer % divider == 0;
    }
}
