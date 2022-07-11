package net.medrag.springexamples.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Stanislav Tretyakov
 * 11.07.2022
 */
@Component
@RequiredArgsConstructor
public class ConfEventCatcher {

    private final SimpleConfig config;

    @EventListener(ApplicationReadyEvent.class)
    public void listen() {
        System.out.println("ConfigurationProperties:");
        System.out.println(config);
        System.out.println();
    }
}
