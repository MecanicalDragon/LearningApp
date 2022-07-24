package net.medrag.microservices.misc.jvalidation.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Stanislav Tretyakov
 * 24.07.2022
 */
public class CustomStringValidator implements ConstraintValidator<Custom, String> {

    private String pattern;

    @Override
    public void initialize(Custom constraintAnnotation) {
        pattern = constraintAnnotation.string();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.contains(pattern);
    }
}
