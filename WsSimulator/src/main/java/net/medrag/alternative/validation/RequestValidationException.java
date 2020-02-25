package net.medrag.alternative.validation;

/**
 * {@author} Stanislav Tretyakov
 * 25.02.2020
 */
public class RequestValidationException extends RuntimeException {
    public RequestValidationException(String message) {
        super(message);
    }
}
