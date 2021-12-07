package net.medrag.microservices.misc.rest.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Stanislav Tretyakov
 * 03.12.2021
 */
@Data
@RequiredArgsConstructor
public class UserV2 {
    private final String name;
    private final String surname;
    private final String contact;
    private final Seniority seniority;
}
