package net.medrag.microservices.jpa.spec.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class SpecDto {
    private final Long id;
    private final LocalDate date;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
}
