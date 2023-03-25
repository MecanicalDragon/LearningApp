package net.medrag.reactivit.reactivitapp.resourcelimiting;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Abstract resource data that holds current resource consumption and a limit.
 * Purpose of this class is to provide methods that allow safely update resource counter and have guarantees that this counter
 * never exceeds some specified limit and never falls below zero, also return explicit result that resource was acquired or not.
 *
 * @author Stanislav Tretyakov
 * 23.12.2022
 */
@ToString
@RequiredArgsConstructor
public final class ResourceCounter {

    private final AtomicInteger consumption = new AtomicInteger(0);
    private final int capacity;

    /**
     * Acquire specified amount of controlled resource.
     * If specified amount, being added to current value exceeds limit, it won't be added, false will be returned.
     */
    public boolean acquire(int amount) {
        final var previousConsumption = consumption.getAndUpdate(currentConsumption -> {
            final var nextConsumption = currentConsumption + amount;
            return nextConsumption > capacity ? currentConsumption : nextConsumption;
        });
        return previousConsumption + amount <= capacity;
    }

    /**
     * Release specified amount of controlled resource.
     * If specified amount, being subtracted from current value makes it fall below zero, it won't be subtracted,
     * false will be returned.
     */
    public void release(int amount) {
        consumption.getAndUpdate(currentConsumption -> {
            final var nextConsumption = currentConsumption - amount;
            return nextConsumption < 0 ? currentConsumption : nextConsumption;
        });
    }
}
