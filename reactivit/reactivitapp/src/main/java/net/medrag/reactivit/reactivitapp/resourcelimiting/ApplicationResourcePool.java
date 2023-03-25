package net.medrag.reactivit.reactivitapp.resourcelimiting;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Map;

/**
 * Pool of resources that can be shared.
 *
 * @author Stanislav Tretyakov
 * 21.12.2022
 */
@ToString
@RequiredArgsConstructor
public class ApplicationResourcePool {

    private final Map<ApplicationResource, ResourceCounter> resourcePool;

    public boolean resourceExists(ApplicationResource applicationResource) {
        return resourcePool.containsKey(applicationResource);
    }

    public boolean acquireResource(ApplicationResource resource, int amount) {
        final var resourceCounter = resourcePool.get(resource);
        if (resourceCounter == null) {
            return false;
        }
        return resourceCounter.acquire(amount);
    }

    public void releaseResource(ApplicationResource resource, int amount) {
        final var resourceCounter = resourcePool.get(resource);
        if (resourceCounter != null) {
            resourceCounter.release(amount);
        }
    }
}
