package net.medrag.PaymentService.service;

import lombok.extern.slf4j.Slf4j;
import net.medrag.PaymentService.model.DatabaseProblemException;
import net.medrag.PaymentService.model.InvalidDataException;
import net.medrag.PaymentService.model.MathematicalCollapseException;
import net.medrag.PaymentService.model.dto.Payment;
import net.medrag.PaymentService.model.entity.PaymentEntity;
import net.medrag.PaymentService.model.event.ShardEnum;
import net.medrag.PaymentService.model.event.ShardConnectionLostEvent;
import net.medrag.PaymentService.service.api.PaymentService;
import net.medrag.PaymentService.service.api.ShardService;
import net.medrag.PaymentService.service.api.ShardAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static net.medrag.PaymentService.model.event.ShardEnum.*;

/**
 * Default implementation of {@link PaymentService}
 * {@author} Stanislav Tretyakov
 * 06.03.2020
 */
@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private final static String LOG_CONNECTION_LOST_EXCEPTION_CASE = "Could not establish connection with database " +
            " %s. Part of request, related to it will not be processed. When connection will be established, try again.";

    /**
     * This value is a threshold for payment list non-async processing.
     * If payment list size exceeds it, job of payments processing
     * will be executed in parallel threads for each database shard.
     */
    @Value("${payment.service.payments.processing.non.async.threshold}")
    private Integer nonAsyncThreshold;

    private ShardService shard1Service;
    private ShardService shard2Service;
    private ShardService shard3Service;

    // stores availability of all database shards
    private ShardAvailability availability;

    // required here to publish events of database crashing
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public PaymentServiceImpl(Shard1Service shard1Service,
                              Shard2Service shard2Service,
                              Shard3Service shard3Service,
                              ShardAvailability availability,
                              ApplicationEventPublisher eventPublisher) {
        this.availability = availability;
        this.shard1Service = shard1Service;
        this.shard2Service = shard2Service;
        this.shard3Service = shard3Service;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Main payments processing method.
     *
     * @return list of aborted payments
     * @see PaymentService#doPayments(List)
     */
    @Override
    public List<Payment> doPayments(List<Payment> payments) {
        int paymentsSize = payments.size();
        log.debug("Payment request have been received. Starting to process data set of {} payments.", paymentsSize);

        // init lists of payment entities, one for each database shard
        List<PaymentEntity> shard1 = new LinkedList<>();
        List<PaymentEntity> shard2 = new LinkedList<>();
        List<PaymentEntity> shard3 = new LinkedList<>();

        // list of payments, that failed to process. Will be returned to requester
        List<Payment> aborted = new LinkedList<>();

        // map each payment to PaymentEntity and add to correspond shard-related list
        for (Payment p : payments) {
            if (p.getAmount() <= 0 || p.getRecipient() <= 0 || p.getSender() <= 0) {
                aborted.add(p);
                continue;
            }

            PaymentEntity payment = PaymentEntity.builder()
                    .sender(p.getSender())
                    .recipient(p.getRecipient())
                    .amount(p.getAmount()).build();

            int shard = (int) (p.getSender() % 3);
            if (shard == 1) shard1.add(payment);
            else if (shard == 2) shard2.add(payment);
            else shard3.add(payment);
        }

        // if payment list is too large, process it asynchronously
        if (paymentsSize > nonAsyncThreshold) {
            log.debug("Processing will be executed asynchronously.");
            aborted.addAll(queryDatabaseShardsAsynchronously(Map.of(SHARD_1, shard1, SHARD_2, shard2, SHARD_3, shard3)));

            // otherwise in current thread
        } else {
            if (shard1.size() != 0) {
                if (!availability.isShard1Available() || !sendRequestToShardAndReturnIfSuccessful(shard1, SHARD_1))
                    aborted.addAll(shard1.stream().map(e -> new Payment().amount(e.getAmount()).sender(e.getSender())
                            .recipient(e.getRecipient())).collect(Collectors.toList()));
            }
            if (shard2.size() != 0) {
                if (!availability.isShard2Available() || !sendRequestToShardAndReturnIfSuccessful(shard2, SHARD_2))
                    aborted.addAll(shard2.stream().map(e -> new Payment().amount(e.getAmount()).sender(e.getSender())
                            .recipient(e.getRecipient())).collect(Collectors.toList()));
            }
            if (shard3.size() != 0) {
                if (!availability.isShard3Available() || !sendRequestToShardAndReturnIfSuccessful(shard3, SHARD_3))
                    aborted.addAll(shard3.stream().map(e -> new Payment().amount(e.getAmount()).sender(e.getSender())
                            .recipient(e.getRecipient())).collect(Collectors.toList()));
            }
        }
        log.debug("{} payments have been processed successfully.", paymentsSize - aborted.size());
        log.debug("{} payments will be returned.", aborted.size());
        return aborted;
    }

    /**
     * Async job with databases absorbed in this method
     *
     * @param shardedPayments - map of {@link ShardEnum} and {@link PaymentEntity} list,
     *                        that should be processed in corresponding shard
     * @return list of aborted payments to inform requester
     */
    private List<Payment> queryDatabaseShardsAsynchronously(Map<ShardEnum, List<PaymentEntity>> shardedPayments) {

        List<PaymentEntity> shard1 = shardedPayments.get(SHARD_1);
        List<PaymentEntity> shard2 = shardedPayments.get(SHARD_2);
        List<PaymentEntity> shard3 = shardedPayments.get(SHARD_3);
        List<Payment> aborted = new LinkedList<>();

        // create and fill map with futures
        Map<ShardEnum, Future<Iterable<PaymentEntity>>> shardedTasks = new HashMap<>();
        if (shard1.size() != 0) {
            if (availability.isShard1Available())
                shardedTasks.put(SHARD_1, shard1Service.addPaymentsToShardAsync(shard1));
            else aborted.addAll(shard1.stream().map(e -> new Payment().amount(e.getAmount())
                    .sender(e.getSender()).recipient(e.getRecipient())).collect(Collectors.toList()));
        }
        if (shard2.size() != 0) {
            if (availability.isShard2Available())
                shardedTasks.put(SHARD_2, shard2Service.addPaymentsToShardAsync(shard2));
            else aborted.addAll(shard2.stream().map(e -> new Payment().amount(e.getAmount())
                    .sender(e.getSender()).recipient(e.getRecipient())).collect(Collectors.toList()));
        }
        if (shard3.size() != 0) {
            if (availability.isShard3Available())
                shardedTasks.put(SHARD_3, shard3Service.addPaymentsToShardAsync(shard3));
            else aborted.addAll(shard3.stream().map(e -> new Payment().amount(e.getAmount())
                    .sender(e.getSender()).recipient(e.getRecipient())).collect(Collectors.toList()));
        }

        // necessary to iterate over futures
        int doneFuturesCounter = 0;
        Map<ShardEnum, Boolean> doneFutures = new HashMap<>();
        doneFutures.put(SHARD_1, false);
        doneFutures.put(SHARD_2, false);
        doneFutures.put(SHARD_3, false);

        // apply async results
        while (doneFuturesCounter < shardedTasks.size()) {
            for (Map.Entry<ShardEnum, Future<Iterable<PaymentEntity>>> entry : shardedTasks.entrySet()) {
                ShardEnum shard = entry.getKey();
                Future future = entry.getValue();
                if (future.isDone() && !doneFutures.get(shard)) {
                    try {
                        doneFuturesCounter++;
                        doneFutures.put(shard, true);
                        future.get();
                        log.debug("{} database shard has finished it's part of work successfully.", shard);
                        continue;
                    } catch (ExecutionException e) {
                        log.error(String.format(LOG_CONNECTION_LOST_EXCEPTION_CASE, shard));
                        if (availability.setShardAvailability(shard, false)) {
                            eventPublisher.publishEvent(new ShardConnectionLostEvent(this, shard));
                        }
                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                        log.error(Arrays.toString(e.getStackTrace()));
                    }
                    aborted.addAll(shardedPayments.get(shard).stream().map(e -> new Payment().amount(e.getAmount())
                            .sender(e.getSender()).recipient(e.getRecipient())).collect(Collectors.toList()));
                } else Thread.yield();
            }
        }
        return aborted;
    }

    /**
     * Aggregates logic of sending payment request to the distinct shard.
     *
     * @param payments - payments to process
     * @param shard    - {@link ShardEnum}
     * @return true if processing was successful, otherwise false
     */
    private boolean sendRequestToShardAndReturnIfSuccessful(List<PaymentEntity> payments, ShardEnum shard) {
        try {
            ShardService shardService = shard == SHARD_1 ? shard1Service : shard == SHARD_2 ? shard2Service : shard3Service;
            shardService.addPaymentsToShard(payments);
            log.debug("{} entries have been added to database {}", payments.size(), shard);
            return true;
        } catch (CannotCreateTransactionException e) {
            log.error(String.format(LOG_CONNECTION_LOST_EXCEPTION_CASE, shard));
            if (availability.setShardAvailability(shard, false)) {
                eventPublisher.publishEvent(new ShardConnectionLostEvent(this, shard));
            }
        }
        return false;
    }

    /**
     * @see PaymentService#getTotalSpending(Long)
     */
    @Override
    public Integer getTotalSpending(Long sender) throws MathematicalCollapseException, InvalidDataException {
        log.debug("Total spending has been requested for sender {}", sender);
        int shard = (int) (Math.abs(sender % 3));
        Integer totalSpending;
        try {
            switch (shard) {
                case 0:
                    log.debug("Requesting Shard 3 database...");
                    totalSpending = shard3Service.getTotalSpendingFromShard(sender);
                    break;
                case 1:
                    log.debug("Requesting Shard 1 database...");
                    totalSpending = shard1Service.getTotalSpendingFromShard(sender);
                    break;
                case 2:
                    log.debug("Requesting Shard 2 database...");
                    totalSpending = shard2Service.getTotalSpendingFromShard(sender);
                    break;
                default:
                    log.error("Traditional mathematics fails! Math.abs({} % 3) = {}, " +
                            "that is not foreseen in our engineerings.", sender, shard);
                    throw new MathematicalCollapseException();
            }
            if (totalSpending == null) {
                String err = String.format("Sender id %s does not exist in database!", sender);
                log.error(err);
                throw new InvalidDataException(err);
            }
            return totalSpending;
        } catch (CannotCreateTransactionException e) {
            ShardEnum shardEnum = shard == 0 ? SHARD_3 : shard == 1 ? SHARD_1 : SHARD_2;
            log.error(String.format(LOG_CONNECTION_LOST_EXCEPTION_CASE, shardEnum));
            if (availability.setShardAvailability(shardEnum, false)) {
                eventPublisher.publishEvent(new ShardConnectionLostEvent(this, shardEnum));
            }
            throw new DatabaseProblemException(e);
        }
    }

}
