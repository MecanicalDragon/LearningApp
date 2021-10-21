package net.medrag.microservices.misc.bpp;

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
public class BppController {

    private final BppServiceApi bppService;

    @GetMapping("/bpp")
    public String callAop(@RequestParam String name, @RequestParam Integer m) {
        return switch (m) {
            case 1 -> bppService.sayHello(name);
            case 2 -> bppService.sayHelloAgain(name);
            default -> bppService.keepSilence(name);
        };
    }

}
