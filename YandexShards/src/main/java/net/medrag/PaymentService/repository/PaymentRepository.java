package net.medrag.PaymentService.repository;

import net.medrag.PaymentService.model.entity.PaymentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * {@author} Stanislav Tretyakov
 * 06.03.2020
 */
@NoRepositoryBean
public interface PaymentRepository extends CrudRepository<PaymentEntity, Long> {

    /**
     * Retrieve total amount spending of account with specified id
     *
     * @param sender - sender's id
     * @return total sender's spending
     */
    @Query("select sum(p.amount) from PaymentEntity p where p.sender = ?1")
    Integer getTotalSpending(Long sender);
}
