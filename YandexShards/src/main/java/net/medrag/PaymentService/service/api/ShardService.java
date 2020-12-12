package net.medrag.PaymentService.service.api;

import net.medrag.PaymentService.model.entity.PaymentEntity;

import java.util.List;
import java.util.concurrent.Future;

/**
 * This service is responsible for interactions with every single database shard
 * <p>
 * {@author} Stanislav Tretyakov
 * 06.03.2020
 */
public interface ShardService {

    /**
     * Retrieves all payments with specified account as sender from database shard
     *
     * @param sender - sender's id
     * @return total value of all sender's payments
     */
    Integer getTotalSpendingFromShard(Long sender);

    /**
     * Add payments to database shard
     *
     * @param payments - list of payments
     * @return collection of persisted payments
     */
    Iterable<PaymentEntity> addPaymentsToShard(List<PaymentEntity> payments);

    /**
     * Add payments to database shard asynchronously
     *
     * @param payments - list of payments
     * @return Future collection of persisted payments
     */
    Future<Iterable<PaymentEntity>> addPaymentsToShardAsync(List<PaymentEntity> payments);
}
