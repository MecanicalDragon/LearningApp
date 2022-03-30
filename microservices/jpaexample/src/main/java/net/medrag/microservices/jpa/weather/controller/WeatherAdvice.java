package net.medrag.microservices.jpa.weather.controller;

import lombok.extern.slf4j.Slf4j;
import net.medrag.microservices.jpa.weather.dto.ErrorDto;
import net.medrag.microservices.jpa.weather.model.EntryDoesNotExistException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @author Stanislav Tretyakov
 * 30.03.2022
 */
@Slf4j
@ControllerAdvice
public class WeatherAdvice {

    @ExceptionHandler(EntryDoesNotExistException.class)
    public ResponseEntity<Void> handleEntityDoesNotExistException(EntryDoesNotExistException e) {
        log.warn("Could not find entry with identifier <{}> in data storage.", e.getId());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e,
            HttpServletRequest httpServletRequest
    ) {
        final List<String> errors = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        final ErrorDto error = new ErrorDto(
                httpServletRequest.getRequestURI(),
                ZonedDateTime.now(),
                BAD_REQUEST.value(),
                BAD_REQUEST.getReasonPhrase(),
                Collections.unmodifiableList(errors)
        );
        log.warn("Invalid request has been received. Errors: [{}]", errors);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception e, HttpServletRequest httpServletRequest) {
        log.error("Unexpected error occurred.", e);
        final ErrorDto error = new ErrorDto(
                httpServletRequest.getRequestURI(),
                ZonedDateTime.now(),
                INTERNAL_SERVER_ERROR.value(),
                INTERNAL_SERVER_ERROR.getReasonPhrase(),
                Collections.singletonList(e.getMessage())
        );
        return ResponseEntity.status(INTERNAL_SERVER_ERROR.value()).body(error);
    }
}
