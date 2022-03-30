package net.medrag.microservices.jpa.spec.dto;

public class SpecUserDoesntExistException extends RuntimeException{

    private final Long id;

    public SpecUserDoesntExistException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
