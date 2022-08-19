package net.medrag.reactivit.reactivitapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.medrag.reactivit.dto.RequestDto;
import net.medrag.reactivit.dto.ResponseDto;
import net.medrag.reactivit.reactivitapp.filter.HeadersFilter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

/**
 * @author Stanislav Tretyakov
 * 22.05.2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MdcService {

    private final WebClient webClient;

    public Mono<String> defaultMono(String source) {
        return Mono.just(source).doOnNext(System.out::println).then(Mono.just("200 Okay"));
    }

    public Mono<ResponseDto> processMonoRequest(RequestDto req) { // 3 9 10 4 5 11 12 7 8
        return Mono.deferContextual(ctx -> {
                MdcUtils.logWithMdc(ctx, () -> log.info(">>>3[{}]", req.getName()));
                final var stringMono = MdcUtils.monoWithMdc(ctx, () -> doSecondRequest(req.getName()))
                    .onErrorResume(e -> {
                        log.error("Hi: {} \n[]\n {}", e.getMessage(), e);
                        return Mono.just("OK");
                    });
                return Mono.deferContextual(ctx2 ->
                        doRequest()
                            .flatMap(r -> MdcUtils.flatMapWithMdc(ctx2, r, resp -> {
                                log.info(">>>4[{}]", req.getName());
                                var x = doRequest();
                                log.info(">>>5[{}]", req.getName());
                                return x;
                            })))
                    .contextWrite(Context.of(HeadersFilter.SENIORITY, req.getName()))
                    .doOnEach(MdcUtils.logOnError(e -> log.info(">>>6[{}] :: {}", req.getName(), e.getMessage())))
                    .then(stringMono)
                    .map(resp -> MdcUtils.mapWithMdc(ctx, resp, r -> {
                        log.info(">>>7[{}]", req.getName());
                        return new ResponseDto(req.getName(), req.getSurname());
                    }))
                    .doOnNext(resp -> MdcUtils.onNextWithMdc(ctx, resp, ee -> log.info(">>>8[{}]", resp.getSeniority())));
            })
            .contextWrite(ctx -> ctx.put(HeadersFilter.NAME, req.getName()))
            .contextWrite(ctx -> ctx.put(HeadersFilter.SNAME, req.getSurname()));
    }

    private Mono<String> doRequest() {
        return webClient.post()
            .uri("http://localhost:8081/sleep", uriBuilder -> uriBuilder
                .queryParam("time", 1000)
                .build())
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class);
    }


    private Mono<String> doSecondRequest(String name) {
        log.info(">>>9[{}]", name);

        if (Math.random() < 1){
            return Mono.error(new RuntimeException("fuck"));
        }

        var result = webClient.post()
            .uri("http://localhost:8081/sleep", uriBuilder -> uriBuilder
                .queryParam("time", 1000)
                .build())
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .onErrorContinue((e, o) -> log.info(">>>13[{}]", name))
            .flatMap(r -> Mono.deferContextual(ctx -> {
                MdcUtils.logWithMdc(ctx, () -> log.info(">>>11[{}]", name));
                var x = doRequest();
                MdcUtils.logWithMdc(ctx, () -> log.info(">>>12[{}]", name));
                doAsync(name);
                return x;
            }));
        log.info(">>>10[{}]", name);
        return result;
    }

    public Disposable doAsync(String name) {
        return Mono.deferContextual(ctx -> {
                MdcUtils.logWithMdc(ctx, () -> log.info(
                    "CHECK OLOLO for name {}",
                    name
                ));
                return Mono.just("OLOLO")
                    .subscribeOn(Schedulers.boundedElastic());
            })
            .contextWrite(ctx -> ctx.put("OLOLO", name))
            .subscribe();
    }
}
