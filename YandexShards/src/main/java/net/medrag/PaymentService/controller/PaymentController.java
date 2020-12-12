package net.medrag.PaymentService.controller;

import net.medrag.PaymentService.controller.api.PaymentApi;
import net.medrag.PaymentService.model.InvalidDataException;
import net.medrag.PaymentService.model.dto.Payment;
import net.medrag.PaymentService.service.api.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link PaymentApi}
 * {@author} Stanislav Tretyakov
 * 06.03.2020
 */
@RestController
public class PaymentController implements PaymentApi {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * @param payment Money operations list (required)
     * @see PaymentApi#doMassPayment(List<Payment>)
     */
    @Override
    public ResponseEntity<List<Payment>> doMassPayment(List<Payment> payment) {
        return ResponseEntity.ok(paymentService.doPayments(payment));
    }

    /**
     * @param payment Money operation transaction (required)
     * @see PaymentApi#doPayment(Payment)
     */
    @Override
    public ResponseEntity<Void> doPayment(Payment payment) {
        List<Payment> payments = paymentService.doPayments(Collections.singletonList(payment));
        if (payments.isEmpty())
            return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<Integer> getTotalSpending(Long id) {
        try {
            return ResponseEntity.ok(paymentService.getTotalSpending(id));
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
