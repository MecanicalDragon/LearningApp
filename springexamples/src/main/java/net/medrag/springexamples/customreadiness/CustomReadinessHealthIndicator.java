package net.medrag.springexamples.customreadiness;

import org.springframework.boot.actuate.availability.ReadinessStateHealthIndicator;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Stanislav Tretyakov
 * 30.08.2022
 */
@Component("readinessStateHealthIndicator")
public class CustomReadinessHealthIndicator extends ReadinessStateHealthIndicator {

    private final AtomicBoolean readiness = new AtomicBoolean(true);

    public CustomReadinessHealthIndicator(ApplicationAvailability availability) {
        super(availability);
    }

    @EventListener(ReadinessEvent.class)
    public void updateReadiness(ReadinessEvent ready) {
        readiness.set(ready.isUp());
    }

    @Override
    protected AvailabilityState getState(ApplicationAvailability applicationAvailability) {
        return readiness.get()
            ? applicationAvailability.getReadinessState()
            : ReadinessState.REFUSING_TRAFFIC;
    }

}

