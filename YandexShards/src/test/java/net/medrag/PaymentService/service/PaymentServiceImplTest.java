package net.medrag.PaymentService.service;

import net.medrag.PaymentService.model.DatabaseProblemException;
import net.medrag.PaymentService.model.InvalidDataException;
import net.medrag.PaymentService.model.dto.Payment;
import net.medrag.PaymentService.model.entity.PaymentEntity;
import net.medrag.PaymentService.model.event.ShardEnum;
import net.medrag.PaymentService.service.api.ShardAvailability;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.List;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
class PaymentServiceImplTest {

    @TestConfiguration
    @ComponentScan("net.medrag")
    static class TestConfig {
    }

    @Autowired
    PaymentServiceImpl paymentService;

    @MockBean
    private Shard1Service shard1Service;
    @MockBean
    private Shard2Service shard2Service;
    @MockBean
    private Shard3Service shard3Service;
    @MockBean
    private ShardAvailability availability;

    private static Payment payment1;
    private static Payment payment2;
    private static Payment payment3;
    private static Payment payment4;
    private static Payment payment5;
    private static Payment payment6;

    @BeforeAll
    static void setUp() {
        payment1 = new Payment().amount(100).sender(13L).recipient(26L);
        payment2 = new Payment().amount(100).sender(26L).recipient(26L);
        payment3 = new Payment().amount(100).sender(45L).recipient(26L);
        payment4 = new Payment().amount(100).sender(31L).recipient(26L);
        payment5 = new Payment().amount(100).sender(33L).recipient(26L);
        payment6 = new Payment().amount(100).sender(363L).recipient(0L);
    }

    /**
     * Test sequential interaction
     * Simulating shards 1 and 2 are unavailable
     */
    @Test
    void doPayments() {
        when(availability.isShard1Available()).thenReturn(false);
        when(availability.isShard2Available()).thenReturn(true);
        when(availability.isShard3Available()).thenReturn(false);

        when(shard2Service.addPaymentsToShard(anyList())).thenThrow(new CannotCreateTransactionException("exception"));

        List<Payment> payments = List.of(payment1, payment2, payment5);
        List<Payment> result = paymentService.doPayments(payments);

        assertEquals(List.of(payment1, payment2, payment5), result);
        verify(shard1Service, never()).addPaymentsToShard(anyList());
        verify(shard2Service, only()).addPaymentsToShard(anyList());
        verify(shard3Service, never()).addPaymentsToShard(anyList());
        verify(availability, times(1)).setShardAvailability(ShardEnum.SHARD_2, false);
    }

    /**
     * Test async interaction
     * Simulating shard 2 goes wrong right now
     */
    @Test
    void doPaymentsAsync1() {
        when(availability.isShard1Available()).thenReturn(true);
        when(availability.isShard2Available()).thenReturn(true);
        when(availability.isShard3Available()).thenReturn(true);

        Future<Iterable<PaymentEntity>> future = new SimpleAsyncTaskExecutor().submit(() -> {
            Thread.sleep(1500);
            return null;
        });
        Future<Iterable<PaymentEntity>> exception = new SimpleAsyncTaskExecutor().submit(() -> {
            Thread.sleep(1500);
            throw new RuntimeException("error");
        });

        when(shard1Service.addPaymentsToShardAsync(anyList())).thenReturn(future);
        when(shard2Service.addPaymentsToShardAsync(anyList())).thenReturn(exception);
        when(shard3Service.addPaymentsToShardAsync(anyList())).thenReturn(future);

        List<Payment> payments = List.of(payment1, payment2, payment3, payment4, payment5, payment6);
        List<Payment> result = paymentService.doPayments(payments);

        assertEquals(List.of(payment6, payment2), result);
        verify(shard1Service, only()).addPaymentsToShardAsync(anyList());
        verify(shard2Service, only()).addPaymentsToShardAsync(anyList());
        verify(shard3Service, only()).addPaymentsToShardAsync(anyList());
        verify(availability, times(1)).setShardAvailability(ShardEnum.SHARD_2, false);
    }

    /**
     * Test async interaction
     * Simulating shard 2 is unavailable
     */
    @Test
    void doPaymentsAsync2() {
        when(availability.isShard1Available()).thenReturn(true);
        when(availability.isShard2Available()).thenReturn(false);
        when(availability.isShard3Available()).thenReturn(true);

        Future<Iterable<PaymentEntity>> future = new SimpleAsyncTaskExecutor().submit(() -> {
            Thread.sleep(1500);
            return null;
        });

        when(shard1Service.addPaymentsToShardAsync(anyList())).thenReturn(future);
        when(shard3Service.addPaymentsToShardAsync(anyList())).thenReturn(future);

        List<Payment> payments = List.of(payment1, payment2, payment3, payment4, payment5, payment6);
        List<Payment> result = paymentService.doPayments(payments);

        System.out.println(List.of(payment6, payment2));
        assertEquals(List.of(payment6, payment2), result);
        verify(shard1Service, only()).addPaymentsToShardAsync(anyList());
        verify(shard2Service, never()).addPaymentsToShardAsync(anyList());
        verify(shard3Service, only()).addPaymentsToShardAsync(anyList());
    }

    /**
     * Testing {@link InvalidDataException} throwing
     *
     * @throws Exception
     */
    @Test
    void getTotalSpendingWithInvalidDataException() {
        when(shard3Service.getTotalSpendingFromShard(15L)).thenReturn(null);
        assertThrows(InvalidDataException.class, () -> {
            paymentService.getTotalSpending(15L);
        });
    }

    /**
     * Testing {@link DatabaseProblemException} throwing
     *
     * @throws Exception
     */
    @Test
    void getTotalSpendingWithDatabaseException() {
        when(shard3Service.getTotalSpendingFromShard(15L)).thenThrow(CannotCreateTransactionException.class);
        when(availability.setShardAvailability(any(ShardEnum.class), anyBoolean())).thenReturn(true);
        assertThrows(DatabaseProblemException.class, () -> {
            paymentService.getTotalSpending(15L);
        });
        verify(availability, only()).setShardAvailability(any(ShardEnum.class), anyBoolean());
    }

    /**
     * Testing correct shard invocation
     *
     * @throws Exception
     */
    @Test
    void getTotalSpendingShard3() throws Exception {
        when(shard3Service.getTotalSpendingFromShard(15L)).thenReturn(200);
        Integer totalSpending = paymentService.getTotalSpending(15L);
        assertEquals(200, totalSpending);
    }

    /**
     * Testing correct shard invocation
     *
     * @throws Exception
     */
    @Test
    void getTotalSpendingShard2() throws Exception {
        when(shard2Service.getTotalSpendingFromShard(14L)).thenReturn(200);
        Integer totalSpending = paymentService.getTotalSpending(14L);
        assertEquals(200, totalSpending);
    }

    /**
     * Testing correct shard invocation
     *
     * @throws Exception
     */
    @Test
    void getTotalSpendingShard1() throws Exception {
        when(shard1Service.getTotalSpendingFromShard(13L)).thenReturn(200);
        Integer totalSpending = paymentService.getTotalSpending(13L);
        assertEquals(200, totalSpending);
    }
}