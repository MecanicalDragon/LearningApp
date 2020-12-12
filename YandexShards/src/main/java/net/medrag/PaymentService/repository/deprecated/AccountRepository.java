package net.medrag.PaymentService.repository.deprecated;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Set;

/**
 * {@author} Stanislav Tretyakov
 * 06.03.2020
 *
 * @Table(name = "ACCOUNT")
 * public class AccountEntity {
 *
 *     @Id
 *     private Long id;
 *
 *     private Integer amount;
 *
 *     @OneToMany(mappedBy = "sender")
 *     private List<PaymentEntity> paymentHistory;
 * }
 */
public interface AccountRepository {

    /**
     * Add specified amount to the balance of account with declared id
     *
     * @param id     - account's id
     * @param amount - amount to add
     * @return - number of modified rows in database
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update AccountEntity a set a.amount = a.amount + ?2 where a.id = ?1")
    Integer increaseBalance(Long id, Integer amount);

    /**
     * Subtract specified amount from the balance of account with declared id
     *
     * @param id     - account's id
     * @param amount - amount to subtract
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update AccountEntity a set a.amount = a.amount - ?2 where a.id = ?1")
    void decreaseBalance(Long id, Integer amount);

    /**
     * Retrieve accounts with specified ids
     *
     * @param ids - id set
     * @return set of accounts
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "4500")})
    Set<Object> getByIdIn(Set<Long> ids);
}
