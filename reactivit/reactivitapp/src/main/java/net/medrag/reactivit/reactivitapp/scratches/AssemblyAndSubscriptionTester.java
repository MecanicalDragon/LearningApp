package net.medrag.reactivit.reactivitapp.scratches;

import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 15.08.2022
 */
public class AssemblyAndSubscriptionTester {

    private final List<String> order = new ArrayList<>();

    public List<String> getOrder() {
        return order;
    }

    public Mono<String> testOrder() {
        order.add("1");
        Mono<String> mono = Mono.just(getString("2"))
            .map(string -> {
                order.add("9");
                return "B";
            })
            .flatMap(string -> {
                order.add("10");
                return Mono.just("C");
            });
        order.add("3");
        return tryLockAndExecute(mono);
    }

    private Mono<String> tryLockAndExecute(Mono<String> operation) {
        order.add("4");
        final var deferredMono = getDeferredMono("8");
        final var immediateMono = getImmediateMono("5");
        return Mono.defer(() -> {
            order.add("6");
            return immediateMono
                .then(deferredMono)
                .then(operation)
                .doOnNext(string -> order.add("11"))
                .then(returnMono());
        });
    }

    private Mono<String> returnMono() {
        order.add("7");
        return Mono.just("E");
    }

    private String getString(String string) {
        order.add(string);
        return string;
    }

    private Mono<String> getImmediateMono(String string) {
        order.add(string);
        return Mono.just(string);
    }

    private Mono<String> getDeferredMono(String string) {
        return Mono.defer(() -> {
            order.add(string);
            return Mono.just(string);
        });
    }
}
