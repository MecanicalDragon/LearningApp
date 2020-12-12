package net.medrag.PaymentService.controller;

import lombok.extern.slf4j.Slf4j;
import net.medrag.PaymentService.model.DatabaseProblemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

/**
 * Catches {@link DatabaseProblemException} and returns http-status 555 to client
 * {@author} Stanislav Tretyakov
 * 06.03.2020
 */
@Slf4j
@ControllerAdvice
public class AdviceController {

    /**
     * If we have a problem with database interaction during transaction, user will se it.
     */
    @ExceptionHandler(value = DatabaseProblemException.class)
    ResponseEntity handleDataAccessException(DatabaseProblemException e) {
        log.error("Exception occurred during database interaction: {}. HTTP status 555 will be returned.", e.getMessage());
        log.error("Don't worry! Stacktrace is foreseen here:");
        log.error(Arrays.toString(e.getCause().getStackTrace()).replaceAll(",", "\n"));
        return ResponseEntity.status(555).build();
    }

    /**
     * OpenApi marks input data error with 500 status code. This fixes it.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity validation500workAround(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
