package net.medrag.reactivit.reactivitapp.resourcelimiting.providers;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.medrag.reactivit.reactivitapp.resourcelimiting.ApplicationResource;
import net.medrag.reactivit.reactivitapp.resourcelimiting.ApplicationResourcePool;

/**
 * ResourceProvider for a resource with a pool of resources within.
 * This pool can be shared.
 *
 * @author Stanislav Tretyakov
 * 21.12.2022
 */
@ToString(exclude = "resourcePool")
@RequiredArgsConstructor
public class PoolBackedResourceProvider implements ResourceProvider {

    private final ApplicationResourcePool resourcePool;
    private final ApplicationResource applicationResource;
    private final int cost;

    @Override
    public boolean tryToAcquireResources() {
        return resourcePool.acquireResource(applicationResource, cost);
    }

    @Override
    public void releaseResources() {
        resourcePool.releaseResource(applicationResource, cost);
    }
}
