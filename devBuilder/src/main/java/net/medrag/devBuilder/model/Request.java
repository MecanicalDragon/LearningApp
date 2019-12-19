package net.medrag.devBuilder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * {@author} Stanislav Tretyakov
 * 21.10.2019
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @NotBlank(message = "Key must not be blank!")
    @Length(min = 2, max = 2, message = "Key length must be exactly 2 symbols!")
    private String key;
    @NotBlank(message = "Input the name!")
    private String name;
    @NotBlank(message = "Input the surname!")
    private String surname;
    @Min(value = 1, message = "Minimal age is 1!")
    @Max(value = 100, message = "Maximal age is 10!")
    private int age;
    @Min(value = 1, message = "Minimal value of skills count is 1!")
    @Max(value = 10, message = "Maximal value of skills count is 10!")
    private int skillsCount;
    @NotBlank(message = "Specify a birthday!")
    @Pattern(regexp = "^[0-3]{1}[0-9]{1}\\.[0-1]{1}[0-9]{1}\\.[0-9]{4} (|[1-2]{1})[0-9]{1}:[0-5]{1}[0-9]{1}$", message = "dateTime pattern is 'dd.MM.yyyy H[H]:mm'!")
    private String bday;
    @NotBlank(message = "Pick a race!")
    private String race;
    private String status;
}
