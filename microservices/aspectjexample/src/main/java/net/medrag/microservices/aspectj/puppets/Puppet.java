package net.medrag.microservices.aspectj.puppets;

/**
 * @author Stanislav Tretyakov
 * 15.12.2021
 */
public class Puppet {
    public String name;
    public String hash;

    @Override
    public String toString() {
        return "Puppet{" +
                "name='" + name + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
