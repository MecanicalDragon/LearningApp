package net.medrag.microservices.aspectj.puppets;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stanislav Tretyakov
 * 15.12.2021
 */
@Service
public class PuppetService {

    private final Map<String, Puppet> puppets = new HashMap<>();

    public void addPuppet(String name) {
        Puppet p = new Puppet();
        p.name = name;
        p.hash = String.valueOf(name.hashCode());
        puppets.put(name, p);
    }

    public void setHash(String name, String hash) {
        final Puppet puppet = puppets.get(name);
        if (puppet != null) {
            puppet.hash = hash;
        }
    }

    public void printPuppets() {
        puppets.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
