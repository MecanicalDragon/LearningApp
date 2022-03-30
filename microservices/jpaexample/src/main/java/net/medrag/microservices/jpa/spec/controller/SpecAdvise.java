package net.medrag.microservices.jpa.spec.controller;

import net.medrag.microservices.jpa.spec.dto.SpecUserDoesntExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SpecAdvise {

    public static final Logger LOGGER = LoggerFactory.getLogger(SpecAdvise.class);

    @ExceptionHandler(SpecUserDoesntExistException.class)
    public ResponseEntity<Void> handleRecordDoesntExistException(SpecUserDoesntExistException e) {
        LOGGER.error("Could not find entry with identifier <{}> in database.", e.getId());
        return ResponseEntity.notFound().build();
    }
}
