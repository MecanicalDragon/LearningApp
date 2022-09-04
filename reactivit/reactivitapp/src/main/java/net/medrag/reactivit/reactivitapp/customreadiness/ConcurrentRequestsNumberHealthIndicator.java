package net.medrag.reactivit.reactivitapp.customreadiness;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Stanislav Tretyakov
 * 30.08.2022
 */
@Component
@RequiredArgsConstructor
public class ConcurrentRequestsNumberHealthIndicator implements ReactiveHealthIndicator {

    private final ConcurrentRequestsProperties concurrentRequestsProperties;

    private final AtomicInteger concurrentRequestsNumber = new AtomicInteger(0);
    private final AtomicBoolean outOfService = new AtomicBoolean(false);

    public void updateReadinessInc() {
        concurrentRequestsNumber.getAndIncrement();
    }

    public void updateReadinessDec() {
        concurrentRequestsNumber.getAndDecrement();
    }

    @Override
    public Mono<Health> health() {
        return Mono
            .just(outOfService.get())
            .map(outOfService ->
                outOfService
                    ? checkIfUpThresholdReached()
                    : checkIfDownThresholdReached()
            );
    }

    private Health checkIfUpThresholdReached() {
        if (concurrentRequestsNumber.get() < concurrentRequestsProperties.getUpThreshold()) {
            outOfService.set(false);
            return Health.up().build();
        } else {
            return Health.down().build();
        }
    }

    private Health checkIfDownThresholdReached() {
        if (concurrentRequestsNumber.get() > concurrentRequestsProperties.getDownThreshold()) {
            outOfService.set(true);
            return Health.down().build();
        } else {
            return Health.up().build();
        }
    }
}

