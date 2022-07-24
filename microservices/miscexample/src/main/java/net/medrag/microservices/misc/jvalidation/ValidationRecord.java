package net.medrag.microservices.misc.jvalidation;

import net.medrag.microservices.misc.jvalidation.custom.Custom;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * @author Stanislav Tretyakov
 * 24.07.2022
 */
public record ValidationRecord(
    @NotBlank(message = "string must not be blank")
    @Size(min = 10, message = "Min size is 10")
    @Custom(string = "tan")
    String string,

    @Min(value = 13, message = "Min value is 13")
    @Positive(message = "Must be positive")
    @Custom(integer = 3)
    int integer
) {
}
