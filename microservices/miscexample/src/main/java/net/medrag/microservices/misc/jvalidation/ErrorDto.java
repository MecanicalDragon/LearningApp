package net.medrag.microservices.misc.jvalidation;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 24.07.2022
 */
@Value
@RequiredArgsConstructor
public class ErrorDto {
    String path;
    ZonedDateTime timestamp;
    int status;
    String message;
    List<String> errors;
}
