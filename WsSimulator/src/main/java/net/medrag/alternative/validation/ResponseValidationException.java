package net.medrag.alternative.validation;

/**
 * {@author} Stanislav Tretyakov
 * 25.02.2020
 */
public class ResponseValidationException extends RuntimeException {
    public ResponseValidationException(String message){
        super(message);
    }
}
