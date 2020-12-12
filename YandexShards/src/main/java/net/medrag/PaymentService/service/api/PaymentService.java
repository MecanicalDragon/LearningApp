package net.medrag.PaymentService.service.api;

import net.medrag.PaymentService.model.InvalidDataException;
import net.medrag.PaymentService.model.MathematicalCollapseException;
import net.medrag.PaymentService.model.dto.Payment;

import java.util.List;

/**
 * Aggregates business logic of payment-related requests processing
 * {@author} Stanislav Tretyakov
 * 06.03.2020
 */
public interface PaymentService {

    /**
     * Do the payment transaction
     *
     * @param payments - list of {@link Payment}
     * @return list of aborted payments
     */
    List<Payment> doPayments(List<Payment> payments);

    /**
     * Retrieves total spending of specified sender
     *
     * @param sender - {@link Payment#sender}
     * @return total spending of specified sender for all time
     * @throws MathematicalCollapseException when traditional mathematic fails
     */
    Integer getTotalSpending(Long sender) throws MathematicalCollapseException, InvalidDataException;
}
