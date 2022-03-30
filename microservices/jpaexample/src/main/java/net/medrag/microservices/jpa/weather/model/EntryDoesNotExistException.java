package net.medrag.microservices.jpa.weather.model;

/**
 * @author Stanislav Tretyakov
 * 30.03.2022
 */
public class EntryDoesNotExistException extends RuntimeException {
    private final Integer id;

    public EntryDoesNotExistException(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
