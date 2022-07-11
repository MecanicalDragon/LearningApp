package net.medrag.springexamples.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Stanislav Tretyakov
 * 13.06.2022
 */
@Component
@RequiredArgsConstructor
public class CommonEventCatcher {

    @EventListener(ApplicationEvent.class)
    public void listen(ApplicationEvent event) {
        System.out.println("ApplicationEvent caught: " + event.getClass().getSimpleName());
        System.out.println();
    }
}
