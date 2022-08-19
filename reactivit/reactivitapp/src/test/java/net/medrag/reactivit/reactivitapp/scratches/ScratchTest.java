package net.medrag.reactivit.reactivitapp.scratches;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScratchTest {

    @BeforeAll
    static void beforeAll() {
        Hooks.onOperatorDebug();
    }

    @Test
    @DisplayName("test that null values can not be neither accepted in Flux, nor emitted")
    void testNull() {
        final var optionalFlux = Flux.<String>fromIterable(List.of("null", "hi"))
            .map(Optional::ofNullable)
            .defaultIfEmpty(Optional.empty())
            .doOnNext(System.out::println);

        StepVerifier.create(optionalFlux)
            .expectNextCount(2)
            .verifyComplete();

        try {
            Flux.<String>fromIterable(List.of(null, "hi"))
                .map(Optional::ofNullable)
                .defaultIfEmpty(Optional.empty())
                .doOnNext(System.out::println)
                .subscribe();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("test assembly and subscription time order understanding")
    void testAssemblyAndSubscription() {
        final var assemblyAndSubscriptionTester = new AssemblyAndSubscriptionTester();
        StepVerifier.create(assemblyAndSubscriptionTester.testOrder())
            .expectNextCount(1)
            .verifyComplete();

        final var order = assemblyAndSubscriptionTester.getOrder();
        System.out.println(order);
        for (int i = 0; i < order.size(); i++) {
            assertEquals(i + 1, Integer.valueOf(order.get(i)));
        }
    }

}