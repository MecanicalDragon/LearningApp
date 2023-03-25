package net.medrag.reactivit.reactivitapp.resourcelimiting.providers;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.medrag.reactivit.reactivitapp.resourcelimiting.ApplicationResource;
import net.medrag.reactivit.reactivitapp.resourcelimiting.ResourceCounter;

/**
 * Provider that watches and manipulates isolated resource.
 *
 * @author Stanislav Tretyakov
 * 21.12.2022
 */
@ToString
@RequiredArgsConstructor
public class IsolatedResourceProvider implements ResourceProvider {

    private final ApplicationResource resource;
    private final ResourceCounter resourceCounter;
    private final int cost;

    @Override
    public boolean tryToAcquireResources() {
        return resourceCounter.acquire(cost);
    }

    @Override
    public void releaseResources() {
        resourceCounter.release(cost);
    }
}
