package net.medrag.reactivit.reactivitapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.medrag.reactivit.dto.RequestDto;
import net.medrag.reactivit.dto.ResponseDto;
import net.medrag.reactivit.reactivitapp.service.MdcService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author Stanislav Tretyakov
 * 22.05.2022
 */
@RestController
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class MdcController {

    private final MdcService mdcService;

    @GetMapping("/get")
    public Mono<String> getDefaultMono(@RequestParam String val){
        return mdcService.defaultMono(val);
    }

    @PostMapping("/post")
    public Mono<ResponseDto> postDefault(@RequestBody Mono<RequestDto> dto){
        return dto.flatMap(mdcService::processMonoRequest);
    }

}
