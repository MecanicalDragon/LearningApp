package net.medrag.microservices.misc.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Stanislav Tretyakov
 * 21.10.2021
 */
@RestController
@RequiredArgsConstructor
public class AopController {
    private final AopService aopService;

    @GetMapping("/aop")
    public String callAop(@RequestParam String name){
        return aopService.sayHello(name);
    }
}
