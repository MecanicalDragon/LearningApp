package net.medrag.PaymentService.service;

import net.medrag.PaymentService.model.event.ShardEnum;
import net.medrag.PaymentService.service.api.ShardAvailability;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Default implementation of {@link ShardAvailability}
 * {@author} Stanislav Tretyakov
 * 10.03.2020
 */
@Service
public class ShardAvailabilityImpl implements ShardAvailability {

    private AtomicBoolean shard1Available;
    private AtomicBoolean shard2Available;
    private AtomicBoolean shard3Available;

    @Override
    public boolean isShard1Available() {
        return shard1Available.get();
    }

    @Override
    public boolean isShard2Available() {
        return shard2Available.get();
    }

    @Override
    public boolean isShard3Available() {
        return shard3Available.get();
    }

    @Override
    public boolean setShardAvailability(ShardEnum shard, boolean available) {
        switch (shard) {
            case SHARD_1:
                return shard1Available.compareAndSet(!available, available);
            case SHARD_2:
                return shard2Available.compareAndSet(!available, available);
            case SHARD_3:
                return shard3Available.compareAndSet(!available, available);
            default:
                return false;
        }
    }

    @PostConstruct
    private void init() {
        shard1Available = new AtomicBoolean(true);
        shard2Available = new AtomicBoolean(true);
        shard3Available = new AtomicBoolean(true);
    }
}
