package net.medrag.microservices.misc.jvalidation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Stanislav Tretyakov
 * 24.07.2022
 */
@Slf4j
@ControllerAdvice
public class ValidationAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e,
        HttpServletRequest httpServletRequest
    ) {
        final List<String> errors = e.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        final ErrorDto error = new ErrorDto(
            httpServletRequest.getRequestURI(),
            ZonedDateTime.now(),
            BAD_REQUEST.value(),
            BAD_REQUEST.getReasonPhrase(),
            errors
        );
        log.warn("Invalid request has been received. Errors: [{}]", errors);
        return ResponseEntity.badRequest().body(error);
    }
}
