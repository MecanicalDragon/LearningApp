package net.medrag.PaymentService.repository.deprecated;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * {@author} Stanislav Tretyakov
 * 06.03.2020
 *
 * @Table(name = "PAYMENT")
 * public class PaymentEntity {
 *
 *     @Id
 *     private Long id;
 *
 *     @ManyToOne
 *     @JoinColumn(name = "SENDER")
 *     private AccountEntity sender;
 *
 *     @ManyToOne(fetch = FetchType.LAZY)
 *     @JoinColumn(name = "RECIPIENT")
 *     private AccountEntity recipient;
 *
 *     private Integer amount;
 * }
 */
public interface PaymentRepository {

    /**
     * Retrieve total amount spending of account with specified id
     *
     * @param id - account's id
     * @return total amount spending
     */
    @Query("select sum(p.amount) from PaymentEntity p where p.sender.id = ?1")
    Integer getTotalSpending(Long id);

    /**
     * Store payment to the database
     *
     * @param amount    - transacted amount
     * @param sender    - Sender's account id
     * @param recipient - Recipient's account id
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "insert into PAYMENT(amount, sender, recipient) " +
            "values (:amount, :sender, :recipient)", nativeQuery = true)
    void addPayment(@Param("amount") Integer amount, @Param("sender") Long sender, @Param("recipient") Long recipient);
}
