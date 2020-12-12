package net.medrag.PaymentService.model;

/**
 * Exception, that indicates, that input data is incorrect
 * {@author} Stanislav Tretyakov
 * 06.03.2020
 */
public class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}
