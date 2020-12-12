package net.medrag.PaymentService.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.medrag.PaymentService.model.dto.Payment;

import javax.persistence.*;

/**
 * Representation of {@link Payment} in the database
 * {@author} Stanislav Tretyakov
 * 06.03.2020
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAYMENT")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "SENDER")
    protected Long sender;

    @Column(name = "RECIPIENT")
    protected Long recipient;

    @Column(name = "AMOUNT")
    protected Integer amount;
}
