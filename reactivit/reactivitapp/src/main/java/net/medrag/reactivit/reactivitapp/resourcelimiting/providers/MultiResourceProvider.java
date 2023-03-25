package net.medrag.reactivit.reactivitapp.resourcelimiting.providers;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * ResourceProvider that aggregates several resource providers to manipulate them together.
 * It's better to convey ArrayList in its constructor to avoid extra complexity during iteration.
 *
 * @author Stanislav Tretyakov
 * 22.12.2022
 */
@ToString
@RequiredArgsConstructor
public class MultiResourceProvider implements ResourceProvider {

    private final List<ResourceProvider> resourceProviders;

    @Override
    public boolean tryToAcquireResources() {
        for (int i = 0; i < resourceProviders.size(); i++) {
            if (!resourceProviders.get(i).tryToAcquireResources()) {
                releaseAcquired(i - 1);
                return false;
            }
        }
        return true;
    }

    @Override
    public void releaseResources() {
        releaseAcquired(resourceProviders.size() - 1);
    }

    private void releaseAcquired(int index) {
        for (int i = index; i >= 0; i--) {
            resourceProviders.get(i).releaseResources();
        }
    }
}
