package net.medrag.microservices.aspectj.tracked;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Stanislav Tretyakov
 * 15.12.2021
 */
@Service
public class TrackedPuppetService {

    private final Map<String, TrackedPuppet> puppets = new HashMap<>();

    public void updatePuppet(String surname, String name, String middleName) {
        TrackedPuppet p = Optional.ofNullable(puppets.get(surname)).orElse(new TrackedPuppet());
        if (!Objects.equals(p.surname, surname)) {
            p.surname = surname;
        }
        if (!Objects.equals(p.name, name)) {
            p.name = name;
        }
        if (!Objects.equals(p.middleName, middleName)) {
            p.middleName = middleName;
        }
        puppets.put(surname, p);
    }

    public void printPuppets() {
        puppets.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
