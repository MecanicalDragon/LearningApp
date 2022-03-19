package net.medrag.microservices.jpa.spec.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class ErrorDto {
    private final ZonedDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
}
