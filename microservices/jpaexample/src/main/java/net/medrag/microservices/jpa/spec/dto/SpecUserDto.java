package net.medrag.microservices.jpa.spec.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@ToString
public class SpecUserDto {
    private final Long id;
    private final LocalDate date;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SpecUserDto(
            @JsonProperty("id") Long id,
            @JsonProperty("date") LocalDate date,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("phoneNumber") String phoneNumber
    ) {
        this.id = id;
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
