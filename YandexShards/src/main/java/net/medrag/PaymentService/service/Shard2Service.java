package net.medrag.PaymentService.service;

import net.medrag.PaymentService.model.entity.PaymentEntity;
import net.medrag.PaymentService.repository.shard2.Shard2PaymentRepository;
import net.medrag.PaymentService.service.api.ShardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Future;

/**
 * {@link ShardService} implementation, responsible for shard 2 interaction
 * {@author} Stanislav Tretyakov
 * 09.03.2020
 */
@Service
public class Shard2Service implements ShardService {

    private Shard2PaymentRepository shardRepository;

    @Autowired
    public Shard2Service(Shard2PaymentRepository shardRepository) {
        this.shardRepository = shardRepository;
    }

    /**
     * @param sender - sender's id
     * @return total value of all sender's payments
     * @see ShardService#getTotalSpendingFromShard(Long)
     */
    @Override
    @Transactional(transactionManager = "shard2DSTransactionManager", readOnly = true)
    public Integer getTotalSpendingFromShard(Long sender) {
        return shardRepository.getTotalSpending(sender);
    }

    /**
     * @param payments - list of payments
     * @see ShardService#addPaymentsToShard(List)
     */
    @Override
    @Transactional(transactionManager = "shard2DSTransactionManager")
    public Iterable<PaymentEntity> addPaymentsToShard(List<PaymentEntity> payments) {
        return shardRepository.saveAll(payments);
    }

    /**
     * @param payments - list of payments
     * @see ShardService#addPaymentsToShardAsync(List)
     */
    @Async
    @Override
    @Transactional(transactionManager = "shard2DSTransactionManager")
    public Future<Iterable<PaymentEntity>> addPaymentsToShardAsync(List<PaymentEntity> payments) {
        return new AsyncResult<>(shardRepository.saveAll(payments));
    }
}
