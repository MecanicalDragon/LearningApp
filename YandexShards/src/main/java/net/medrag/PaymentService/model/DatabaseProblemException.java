package net.medrag.PaymentService.model;

/**
 * Throw it, when database connection problems occurred
 * {@author} Stanislav Tretyakov
 * 09.03.2020
 */
public class DatabaseProblemException extends RuntimeException {
    public DatabaseProblemException(Throwable cause) {
        super(cause);
    }
}
