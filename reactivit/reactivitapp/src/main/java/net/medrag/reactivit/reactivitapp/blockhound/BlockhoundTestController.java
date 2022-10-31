package net.medrag.reactivit.reactivitapp.blockhound;

/**
 * @author Stanislav Tretyakov
 * 07.09.2022
 */

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/bh")
@RequiredArgsConstructor
public class BlockhoundTestController {


    @GetMapping("/block")
    @SneakyThrows
    public Mono<ResponseEntity<String>> block() {
        log.info(">>+ hello " + Thread.currentThread().getName() );
        Thread.sleep(1000);
        log.info(">>+ bye " + Thread.currentThread().getName() );
        return Mono.just(ResponseEntity.ok(""));
    }
}
