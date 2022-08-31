package net.medrag.springexamples.customreadiness;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Stanislav Tretyakov
 * 30.08.2022
 */
@Slf4j
@RestController
@RequestMapping("/ready")
@RequiredArgsConstructor
public class ReadinessController {

    private final ApplicationEventPublisher eventPublisher;

    @GetMapping("/on")
    public String turnOn(){
        eventPublisher.publishEvent(ReadinessEvent.readinessUp(this));
        return "on";
    }

    @GetMapping("/off")
    public String turnOff(){
        eventPublisher.publishEvent(ReadinessEvent.readinessDown(this));
        return "off";
    }
}
