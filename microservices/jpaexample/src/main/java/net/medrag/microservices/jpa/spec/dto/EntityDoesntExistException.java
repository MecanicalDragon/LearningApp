package net.medrag.microservices.jpa.spec.dto;

public class EntityDoesntExistException extends RuntimeException{

    private final Long id;

    public EntityDoesntExistException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
