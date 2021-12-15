package net.medrag.microservices.aspectj.tracked;

/**
 * @author Stanislav Tretyakov
 * 15.12.2021
 */
public class TrackedPuppet {
    @Tracked(tracked = false, permitted = true)
    public String name;
    @Tracked(tracked = true, permitted = true)
    public String surname;
    @Tracked(tracked = true, permitted = false)
    public String middleName;

    @Override
    public String toString() {
        return "TrackedPuppet{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                '}';
    }
}
