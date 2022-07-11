package net.medrag.springexamples.qualifiers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Stanislav Tretyakov
 * 13.06.2022
 */
@Component
@RequiredArgsConstructor
public class QEventCatcher {

    /**
     * This field is set because of Spring beans resolution by name.
     */
    private final QInterface qin1;

    /**
     * This field is set because of lombok.config
     */
    @Qualifier("two")
    private final QInterface qInterface;

    @EventListener(ApplicationReadyEvent.class)
    public void listen() {
        System.out.println("Qualifiers::");
        System.out.println(qin1.getString());
        System.out.println(qInterface.getString());
        System.out.println();
    }
}
