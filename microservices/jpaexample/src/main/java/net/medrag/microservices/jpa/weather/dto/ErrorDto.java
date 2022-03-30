package net.medrag.microservices.jpa.weather.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 30.03.2022
 */
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class ErrorDto {
    private final String path;
    private final ZonedDateTime timestamp;
    private final int status;
    private final String message;
    private final List<String> errors;
}
