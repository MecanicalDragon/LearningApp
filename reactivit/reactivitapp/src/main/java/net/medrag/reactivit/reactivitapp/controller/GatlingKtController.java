package net.medrag.reactivit.reactivitapp.controller;

/**
 * @author Stanislav Tretyakov
 * 19.08.2022
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.medrag.reactivit.dto.RequestDto;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/gatling")
@RequiredArgsConstructor
public class GatlingKtController {

    private final Map<String, RequestDto> dataSource = Map.of(
        "Overlord", new RequestDto("Stanislav", "Tretiakov"),
        "Lead", new RequestDto("Denis", "Aurum"),
        "Senior", new RequestDto("Oleg", "Shmoleg"),
        "Middle", new RequestDto("Evgeniy", "Bazenov"),
        "Junior", new RequestDto("Nikolay", "Berkutov"),
        "Trainee", new RequestDto("Sergey", "Brokkoly")
    );

    @GetMapping("/get-ok")
    public Mono<Void> get() {
        return Mono.when();
    }

    @GetMapping("/number")
    public Mono<RequestDto> getByNumber(@NotNull @RequestParam int index) {
        return Mono.just(index)
            .map(i -> i % dataSource.size())
            .map(i -> dataSource.values().toArray(new RequestDto[0])[i]);
    }

    @GetMapping("/coder")
    public Mono<RequestDto> getCoder(@NotNull @RequestParam String seniority) {
        return Mono.just(seniority)
            .map(dataSource::get);
    }

    @PostMapping("/seniority")
    public Mono<String> getSeniority(@NotNull @RequestBody Mono<RequestDto> request) {
        return request.map(req ->
            dataSource.entrySet().stream()
                .filter(entry -> entry.getValue().equals(req))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse("null"));
    }
}
