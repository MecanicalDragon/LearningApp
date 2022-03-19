package net.medrag.microservices.jpa.spec.controller;

import net.medrag.microservices.jpa.spec.dto.EntityDoesntExistException;
import net.medrag.microservices.jpa.spec.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

@ControllerAdvice
public class AdvisedController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AdvisedController.class);
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

    @ExceptionHandler(EntityDoesntExistException.class)
    public ResponseEntity<Void> handleRecordDoesntExistException(EntityDoesntExistException e) {
        LOGGER.error("Could not find entry with identifier <{}> in database.", e.getId());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception e, HttpServletRequest httpServletRequest) {
        LOGGER.error("Unexpected error occurred.", e);
        final ErrorDto error = new ErrorDto(
                ZonedDateTime.now(),
                500,
                INTERNAL_SERVER_ERROR,
                INTERNAL_SERVER_ERROR,
                httpServletRequest.getRequestURI()
        );
        return ResponseEntity.status(500).body(error);
    }
}
