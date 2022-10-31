package net.medrag.reactivit.reactivitapp.retry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Stanislav Tretyakov
 * 31.10.2022
 */
@Slf4j
@RestController
@RequestMapping("/retry")
@RequiredArgsConstructor
public class RetryController {

    private final WebClient webClient;

    private final AtomicInteger counter = new AtomicInteger(0);

    @GetMapping
    public Mono<ResponseEntity<String>> doRetry() {
        System.out.println("request accepted");
        return webClient
            .get()
            .uri("http://localhost:34344/retry/test")
            .retrieve()
            .bodyToMono(String.class)
            .map(ResponseEntity::ok);
    }

    @GetMapping("/test")
    public Mono<ResponseEntity<String>> test() {
        final var i = counter.incrementAndGet();
        System.out.println("attempt " + i + "...");
        if (i >= 3) {
            counter.set(0);
            System.out.println("OK");
            return Mono.just(ResponseEntity.ok().build());
        } else {
            System.out.println(503);
            return Mono.just(ResponseEntity.status(503).build());
        }
    }
}
