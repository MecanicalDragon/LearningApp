package net.medrag.reactivit.reactivitapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.medrag.reactivit.dto.RequestDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final WebClient webClient;

    /**
     * Test {@link Mono#thenReturn(Object)}.
     */
    @GetMapping("/")
    public Mono<ResponseEntity<Void>> executeCallback() {
        return Mono.deferContextual(ctx -> Mono.empty())
            .contextWrite(ctx -> ctx.put("aaa", "bbb"))
            .thenReturn(ResponseEntity.noContent().build());
    }

    /**
     * Test nothing for a while. In future we want to test async behavior.
     */
    @GetMapping("/async")
    public Mono<ResponseEntity<Void>> testAsync() {
        return Flux.fromArray(getSource())
            .flatMap(this::asyncFunction)
            .collectList().doOnNext(System.out::println)
            .switchIfEmpty(Mono.just(List.of(null)))
            .doOnNext(System.out::println)
            .thenReturn(ResponseEntity.ok().build());
    }

    private Mono<RequestDto> asyncFunction(String source) {
        return webClient.get()
            .uri("http://localhost:34343/sleep", uriBuilder -> uriBuilder
                .queryParam("time", 1000)
                .queryParam("string", source)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(RequestDto.class);
    }

    private String[] getSource() {
        return new String[]{"oNe", "tWo", "tHrEe"};
    }
}
