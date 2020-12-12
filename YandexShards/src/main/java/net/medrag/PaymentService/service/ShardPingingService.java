package net.medrag.PaymentService.service;

import lombok.extern.slf4j.Slf4j;
import net.medrag.PaymentService.model.InvalidDataException;
import net.medrag.PaymentService.model.event.ShardConnectionLostEvent;
import net.medrag.PaymentService.model.event.ShardEnum;
import net.medrag.PaymentService.service.api.PaymentService;
import net.medrag.PaymentService.service.api.ShardAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * This component does scheduled work of obtaining lost connections with database shards
 * {@author} Stanislav Tretyakov
 * 09.03.2020
 */
@Slf4j
@Component
public class ShardPingingService {

    @Value("${payment.service.database.ping.period.in.sec}")
    private Integer pingPeriod;
    private ScheduledExecutorService executorService;
    private ScheduledFuture shard1ConnectionTestingTask;
    private ScheduledFuture shard2ConnectionTestingTask;
    private ScheduledFuture shard3ConnectionTestingTask;
    private ShardAvailability shardAvailability;
    private PaymentService paymentService;

    @Autowired
    public ShardPingingService(ShardAvailability shardAvailability, PaymentService paymentService) {
        this.shardAvailability = shardAvailability;
        this.paymentService = paymentService;
    }

    @EventListener
    public void launchJob(ShardConnectionLostEvent event) {
        ShardEnum shard = event.getShardEnum();
        log.info(String.format("Starting job of establishing connection with database %s...", shard));
        switch (shard) {
            case SHARD_1:
                shard1ConnectionTestingTask = executorService.scheduleWithFixedDelay(
                        buildShardPingingTask(shard, shard1ConnectionTestingTask, -1L), 15, pingPeriod, TimeUnit.SECONDS);
                break;
            case SHARD_2:
                shard2ConnectionTestingTask = executorService.scheduleWithFixedDelay(
                        buildShardPingingTask(shard, shard2ConnectionTestingTask, -2L), 15, pingPeriod, TimeUnit.SECONDS);
                break;
            case SHARD_3:
                shard3ConnectionTestingTask = executorService.scheduleWithFixedDelay(
                        buildShardPingingTask(shard, shard3ConnectionTestingTask, -3L), 15, pingPeriod, TimeUnit.SECONDS);
                break;
        }
    }

    /**
     * prepare scheduled task for work with specified database shard
     *
     * @param shard - shard, that has lost connection
     * @param task  - task to cancel when connection is established
     * @param id    - for ping requests to database
     * @return - scheduled task
     */
    private Runnable buildShardPingingTask(ShardEnum shard, ScheduledFuture task, Long id) {
        return () -> {
            try {
                log.info(String.format("Trying to establish connection with database %s...", shard));
                paymentService.getTotalSpending(id);
            } catch (InvalidDataException e) {
                log.info(String.format("Success! Connection with database %s established.", shard));
                shardAvailability.setShardAvailability(shard, true);
                task.cancel(false);
            } catch (Exception ignored) {
                log.warn(String.format("Attempt to establish connection with database %s has failed.", shard));
            }
        };
    }

    @PostConstruct
    private void init() {
        executorService = Executors.newScheduledThreadPool(3);
    }
}
