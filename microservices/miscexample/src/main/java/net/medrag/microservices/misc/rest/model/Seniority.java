package net.medrag.microservices.misc.rest.model;

import java.util.Arrays;

/**
 * @author Stanislav Tretyakov
 * 03.12.2021
 */
public enum Seniority {
    TRAINEE("стажер"),
    JUNIOR("джун"),
    REGULAR("разраб"),
    SENIOR("крутой"),
    LEAD("батя"),
    OVERLORD("повелитель");

    private final String ruName;

    Seniority(String ruName) {
        this.ruName = ruName;
    }

    public static Seniority from(String description) {
        return Arrays.stream(values()).filter(s -> s.ruName.equals(description)).findFirst().orElseThrow();
    }
}
