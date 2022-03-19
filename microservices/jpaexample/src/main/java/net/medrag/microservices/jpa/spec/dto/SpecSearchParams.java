package net.medrag.microservices.jpa.spec.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class SpecSearchParams {
    private final List<String> phoneNumber;
    private final List<Long> id;
    private final List<LocalDate> date;
    private final List<String> firstName;
    private final List<String> lastName;

    public SpecSearchParams(List<String> phoneNumber, List<Long> id, List<LocalDate> date, List<String> firstName, List<String> lastName) {
        this.phoneNumber = phoneNumber == null ? Collections.emptyList() : Collections.unmodifiableList(phoneNumber);
        this.id = id == null ? Collections.emptyList() : Collections.unmodifiableList(id);
        this.date = date == null ? Collections.emptyList() : Collections.unmodifiableList(date);
        this.firstName = firstName == null ? Collections.emptyList() : Collections.unmodifiableList(firstName);
        this.lastName = lastName == null ? Collections.emptyList() : Collections.unmodifiableList(lastName);
    }
}
