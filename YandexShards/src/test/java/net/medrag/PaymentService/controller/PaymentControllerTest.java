package net.medrag.PaymentService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.medrag.PaymentService.PaymentServiceApplication;
import net.medrag.PaymentService.model.dto.Payment;
import net.medrag.PaymentService.model.entity.PaymentEntity;
import net.medrag.PaymentService.repository.shard1.Shard1PaymentRepository;
import net.medrag.PaymentService.repository.shard2.Shard2PaymentRepository;
import net.medrag.PaymentService.repository.shard3.Shard3PaymentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PaymentServiceApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
class PaymentControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private Shard1PaymentRepository shard1repo;

    @Autowired
    private Shard2PaymentRepository shard2repo;

    @Autowired
    private Shard3PaymentRepository shard3repo;

    private static Payment payment1;
    private static Payment payment2;
    private static Payment payment3;
    private static Payment payment4;
    private static Payment payment5;
    private static Payment payment6;

    @BeforeAll
    static void setUp() {
    }

    @BeforeEach
    void setUd() {
        PaymentEntity paymentEntity1 = PaymentEntity.builder().id(1L).amount(1).sender(13L).recipient(50L).build();
        PaymentEntity paymentEntity2 = PaymentEntity.builder().id(2L).amount(20).sender(26L).recipient(15L).build();
        PaymentEntity paymentEntity3 = PaymentEntity.builder().id(3L).amount(300).sender(45L).recipient(75L).build();
        PaymentEntity paymentEntity4 = PaymentEntity.builder().id(4L).amount(4000).sender(13L).recipient(33L).build();
        PaymentEntity paymentEntity5 = PaymentEntity.builder().id(5L).amount(50000).sender(182L).recipient(120L).build();
        PaymentEntity paymentEntity6 = PaymentEntity.builder().id(6L).amount(600000).sender(363L).recipient(240L).build();

        payment1 = new Payment().amount(100).sender(13L).recipient(26L);
        payment2 = new Payment().amount(100).sender(26L).recipient(26L);
        payment3 = new Payment().amount(100).sender(45L).recipient(26L);
        payment4 = new Payment().amount(100).sender(31L).recipient(26L);
        payment5 = new Payment().amount(100).sender(33L).recipient(26L);
        payment6 = new Payment().amount(100).sender(363L).recipient(26L);

        shard1repo.saveAll(List.of(paymentEntity1, paymentEntity4));
        shard2repo.saveAll(List.of(paymentEntity2, paymentEntity5));
        shard3repo.saveAll(List.of(paymentEntity3, paymentEntity6));
    }

    @AfterEach
    void tearDown() {
        shard1repo.deleteAll();
        shard2repo.deleteAll();
        shard3repo.deleteAll();
    }

    /**
     * Testing {@link PaymentController#getTotalSpending(Long)}
     *
     * @throws Exception
     */
    @Test
    void getTotalSpending() throws Exception {
        mvc.perform(get("/payment/bySender/13"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(4001));
        mvc.perform(get("/payment/bySender/26"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(20));
        mvc.perform(get("/payment/bySender/777"))
                .andExpect(status().is(400));
    }

    /**
     * Testing {@link PaymentController#doPayment(Payment)}
     *
     * @throws Exception
     */
    @Test
    void doPayment() throws Exception {
        mvc.perform(post("/payment/add").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper()
                .writeValueAsString(payment1)))
                .andExpect(status().isOk());
        mvc.perform(post("/payment/add").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper()
                .writeValueAsString(payment2)))
                .andExpect(status().isOk());
        mvc.perform(post("/payment/add").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper()
                .writeValueAsString(payment3)))
                .andExpect(status().isOk());

        Integer sender13 = shard1repo.getTotalSpending(13L);
        Integer sender26 = shard2repo.getTotalSpending(26L);
        Integer sender45 = shard3repo.getTotalSpending(45L);

        Assertions.assertEquals(4101, sender13);
        Assertions.assertEquals(120, sender26);
        Assertions.assertEquals(400, sender45);
    }

    /**
     * Testing {@link PaymentController#doMassPayment(List)}
     *
     * @throws Exception
     */
    @Test
    void doMassPayment() throws Exception {
        mvc.perform(post("/payment/addList").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(List.of(payment1, payment3, payment6))))
                .andExpect(status().isOk());
        mvc.perform(post("/payment/addList").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(List.of(payment5, payment5, payment3))))
                .andExpect(status().isOk());
        mvc.perform(post("/payment/addList").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(List.of(payment1))))
                .andExpect(status().isOk());

        List<PaymentEntity> repo1 = (List<PaymentEntity>) shard1repo.findAll();
        List<PaymentEntity> repo2 = (List<PaymentEntity>) shard2repo.findAll();
        List<PaymentEntity> repo3 = (List<PaymentEntity>) shard3repo.findAll();

        Integer sender13 = repo1.stream().filter(e -> e.getSender().equals(13L))
                .map(PaymentEntity::getAmount).reduce(0, Integer::sum);
        Integer sender33 = repo3.stream().filter(e -> e.getSender().equals(33L))
                .map(PaymentEntity::getAmount).reduce(0, Integer::sum);
        Integer sender45 = repo3.stream().filter(e -> e.getSender().equals(45L))
                .map(PaymentEntity::getAmount).reduce(0, Integer::sum);
        Integer sender363 = repo3.stream().filter(e -> e.getSender().equals(363L))
                .map(PaymentEntity::getAmount).reduce(0, Integer::sum);
        Integer senderInvalid = repo2.stream().filter(e -> e.getSender().equals(13L))
                .map(PaymentEntity::getAmount).reduce(0, Integer::sum);

        Assertions.assertEquals(4201, sender13);
        Assertions.assertEquals(200, sender33);
        Assertions.assertEquals(500, sender45);
        Assertions.assertEquals(600100, sender363);
        Assertions.assertEquals(0, senderInvalid);
    }

    /**
     * Testing {@link PaymentController#doMassPayment(List)} asynchronous services invocation
     *
     * @throws Exception
     */
    @Test
    void doMassPaymentAsync() throws Exception {
        mvc.perform(post("/payment/addList").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(List.of(payment1, payment2, payment3, payment4, payment5, payment6))))
                .andExpect(status().isOk());

        List<PaymentEntity> repo1 = (List<PaymentEntity>) shard1repo.findAll();
        List<PaymentEntity> repo2 = (List<PaymentEntity>) shard2repo.findAll();
        List<PaymentEntity> repo3 = (List<PaymentEntity>) shard3repo.findAll();

        Integer sender13 = repo1.stream().filter(e -> e.getSender().equals(13L))
                .map(PaymentEntity::getAmount).reduce(0, Integer::sum);
        Integer sender33 = repo3.stream().filter(e -> e.getSender().equals(33L))
                .map(PaymentEntity::getAmount).reduce(0, Integer::sum);
        Integer sender45 = repo3.stream().filter(e -> e.getSender().equals(45L))
                .map(PaymentEntity::getAmount).reduce(0, Integer::sum);
        Integer sender363 = repo3.stream().filter(e -> e.getSender().equals(363L))
                .map(PaymentEntity::getAmount).reduce(0, Integer::sum);
        Integer senderInvalid = repo2.stream().filter(e -> e.getSender().equals(13L))
                .map(PaymentEntity::getAmount).reduce(0, Integer::sum);

        Assertions.assertEquals(4101, sender13);
        Assertions.assertEquals(100, sender33);
        Assertions.assertEquals(400, sender45);
        Assertions.assertEquals(600100, sender363);
        Assertions.assertEquals(0, senderInvalid);
    }
}