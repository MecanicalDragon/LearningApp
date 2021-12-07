package net.medrag.microservices.misc.rest.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Stanislav Tretyakov
 * 03.12.2021
 */
@Data
@RequiredArgsConstructor
public class User {
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String phone;
    private final String address;
    private final String email;
}
