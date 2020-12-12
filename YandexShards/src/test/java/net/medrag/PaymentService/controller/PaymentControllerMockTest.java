package net.medrag.PaymentService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.medrag.PaymentService.PaymentServiceApplication;
import net.medrag.PaymentService.model.dto.Payment;
import net.medrag.PaymentService.model.entity.PaymentEntity;
import net.medrag.PaymentService.service.Shard1Service;
import net.medrag.PaymentService.service.Shard2Service;
import net.medrag.PaymentService.service.Shard3Service;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.concurrent.Future;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Verifying valid interactions with shard repositories
 * WARNING!!! TESTS WILL BE VALID WITH 'payment.service.payments.processing.non.async.threshold' PROPERTY EQUAL TO 4
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PaymentServiceApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
class PaymentControllerMockTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private Shard1Service shard1Service;

    @MockBean
    private Shard2Service shard2Service;

    @MockBean
    private Shard3Service shard3Service;

    private static Payment payment1;
    private static Payment payment2;
    private static Payment payment3;
    private static Payment payment4;
    private static Payment payment5;
    private static Payment payment6;

    @BeforeAll
    static void setUp() {

        payment1 = new Payment().amount(100).sender(13L).recipient(26L);
        payment2 = new Payment().amount(100).sender(14L).recipient(26L);
        payment3 = new Payment().amount(100).sender(15L).recipient(26L);
        payment4 = new Payment().amount(100).sender(31L).recipient(26L);
        payment5 = new Payment().amount(100).sender(32L).recipient(26L);
        payment6 = new Payment().amount(100).sender(33L).recipient(26L);
    }

    @BeforeEach
    void setUd() {
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Verifying interaction only with {@link this#shard1Service}
     *
     * @throws Exception
     */
    @Test
    void getTotalSpendingFromShard1() throws Exception {
        mvc.perform(get("/payment/bySender/13"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk());
        verify(shard1Service, only()).getTotalSpendingFromShard(13L);
        verify(shard2Service, never()).getTotalSpendingFromShard(anyLong());
        verify(shard3Service, never()).getTotalSpendingFromShard(anyLong());
    }

    /**
     * Verifying interaction only with {@link this#shard2Service}
     *
     * @throws Exception
     */
    @Test
    void getTotalSpendingFromShard2() throws Exception {
        mvc.perform(get("/payment/bySender/26"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk());
        verify(shard1Service, never()).getTotalSpendingFromShard(anyLong());
        verify(shard2Service, only()).getTotalSpendingFromShard(26L);
        verify(shard3Service, never()).getTotalSpendingFromShard(anyLong());
    }

    /**
     * Verifying interaction only with {@link this#shard3Service}
     *
     * @throws Exception
     */
    @Test
    void getTotalSpendingFromShard3() throws Exception {
        mvc.perform(get("/payment/bySender/45"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk());
        verify(shard1Service, never()).getTotalSpendingFromShard(anyLong());
        verify(shard2Service, never()).getTotalSpendingFromShard(anyLong());
        verify(shard3Service, only()).getTotalSpendingFromShard(45L);
    }

    /**
     * Verifying interaction only with {@link this#shard1Service}
     *
     * @throws Exception
     */
    @Test
    void doPaymentToShard1() throws Exception {
        mvc.perform(post("/payment/add").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper()
                .writeValueAsString(payment1)))
                .andExpect(status().isOk());
        verify(shard1Service, only()).addPaymentsToShard(anyList());
        verify(shard2Service, never()).addPaymentsToShard(anyList());
        verify(shard3Service, never()).addPaymentsToShard(anyList());
    }

    /**
     * Verifying interaction only with {@link this#shard2Service}
     *
     * @throws Exception
     */
    @Test
    void doPaymentToShard2() throws Exception {
        mvc.perform(post("/payment/add").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper()
                .writeValueAsString(payment2)))
                .andExpect(status().isOk());
        verify(shard1Service, never()).addPaymentsToShard(anyList());
        verify(shard2Service, only()).addPaymentsToShard(anyList());
        verify(shard3Service, never()).addPaymentsToShard(anyList());
    }

    /**
     * Verifying interaction only with {@link this#shard3Service}
     *
     * @throws Exception
     */
    @Test
    void doPaymentToShard3() throws Exception {
        mvc.perform(post("/payment/add").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper()
                .writeValueAsString(payment3)))
                .andExpect(status().isOk());
        verify(shard1Service, never()).addPaymentsToShard(anyList());
        verify(shard2Service, never()).addPaymentsToShard(anyList());
        verify(shard3Service, only()).addPaymentsToShard(anyList());
    }

    /**
     * Verifying interaction only with {@link this#shard1Service} and {@link this#shard2Service}
     *
     * @throws Exception
     */
    @Test
    void doMassPayment1() throws Exception {
        mvc.perform(post("/payment/addList").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper()
                .writeValueAsString(List.of(payment1, payment2))))
                .andExpect(status().isOk());
        verify(shard1Service, only()).addPaymentsToShard(anyList());
        verify(shard2Service, only()).addPaymentsToShard(anyList());
        verify(shard3Service, never()).addPaymentsToShard(anyList());
    }

    /**
     * Verifying interaction only with {@link this#shard3Service} and {@link this#shard2Service}
     *
     * @throws Exception
     */
    @Test
    void doMassPayment2() throws Exception {
        mvc.perform(post("/payment/addList").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper()
                .writeValueAsString(List.of(payment2, payment3))))
                .andExpect(status().isOk());
        verify(shard1Service, never()).addPaymentsToShard(anyList());
        verify(shard2Service, only()).addPaymentsToShard(anyList());
        verify(shard3Service, only()).addPaymentsToShard(anyList());
    }

    /**
     * Verifying interaction only with {@link this#shard1Service} and {@link this#shard3Service}
     *
     * @throws Exception
     */
    @Test
    void doMassPayment3() throws Exception {
        mvc.perform(post("/payment/addList").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper()
                .writeValueAsString(List.of(payment1, payment3))))
                .andExpect(status().isOk());
        verify(shard1Service, only()).addPaymentsToShard(anyList());
        verify(shard2Service, never()).addPaymentsToShard(anyList());
        verify(shard3Service, only()).addPaymentsToShard(anyList());
    }

    /**
     * Verifying async methods invocations
     *
     * @throws Exception
     */
    @Test
    void doMassPaymentAsynchronously() throws Exception {

        Future<Iterable<PaymentEntity>> future = new SimpleAsyncTaskExecutor().submit(() -> {
            Thread.sleep(1500);
            return null;
        });

        when(shard1Service.addPaymentsToShardAsync(anyList())).thenReturn(future);
        when(shard2Service.addPaymentsToShardAsync(anyList())).thenReturn(future);
        when(shard3Service.addPaymentsToShardAsync(anyList())).thenReturn(future);

        mvc.perform(post("/payment/addList").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper()
                .writeValueAsString(List.of(payment1, payment2, payment3, payment4, payment5, payment6))))
                .andExpect(status().isOk());
        verify(shard1Service, only()).addPaymentsToShardAsync(anyList());
        verify(shard2Service, only()).addPaymentsToShardAsync(anyList());
        verify(shard3Service, only()).addPaymentsToShardAsync(anyList());
    }
}