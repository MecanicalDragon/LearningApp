package net.medrag.PaymentService.service.api;

import net.medrag.PaymentService.model.event.ShardEnum;

/**
 * Service, that stores availability state of the database shards
 * {@author} Stanislav Tretyakov
 * 10.03.2020
 */
public interface ShardAvailability {
    boolean isShard1Available();
    boolean isShard2Available();
    boolean isShard3Available();
    boolean setShardAvailability(ShardEnum shard, boolean available);
}
