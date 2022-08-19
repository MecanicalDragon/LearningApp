package net.medrag.reactivit.reactivitapp.bombarder.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.medrag.reactivit.dto.RequestDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Stanislav Tretyakov
 * 22.05.2022
 */
@RestController
@RequiredArgsConstructor
public class SleepController {

    private final BombarderService bombarderService;

    @PostMapping("/switch")
    public String switchDispatch() {
        return bombarderService.switchDispatch();
    }

    @PostMapping("/hit")
    public String hit() {
        return bombarderService.hit();
    }

    @PostMapping("/sleep")
    @SneakyThrows
    public String sleep(@RequestParam long time) {
        Thread.sleep(time);
        return "200 OK";
    }

    @GetMapping("/sleep")
    @SneakyThrows
    public RequestDto sleepAndGet(@RequestParam long time, @RequestParam String string) {
        Thread.sleep(time);
        return new RequestDto(string.toLowerCase(), string.toUpperCase());
    }
}
