package net.medrag.reactivit.reactivitapp.resourcelimiting.config;

import lombok.Value;
import net.medrag.reactivit.reactivitapp.resourcelimiting.ApplicationResource;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

/**
 * Resource limit for specific endpoint.
 *
 * @author Stanislav Tretyakov
 * 21.12.2022
 */
@Value
public class ResourceLimit {

    /**
     * Resource abstract representation.
     */
    ApplicationResource resource;

    /**
     * Personal resource limit for specific endpoint.
     */
    int personalLimit;

    /**
     * Cost of single resource acquisition for specific endpoint.
     * This cost is valid on application level too, but only for this specific endpoint.
     */
    int cost;

    public ResourceLimit(
        @NonNull ApplicationResource resource,
        @Nullable Integer personalLimit,
        @Nullable Integer cost
    ) {
        this.resource = resource;
        this.personalLimit = personalLimit == null ? 0 : Math.max(personalLimit, 0);
        this.cost = cost == null || cost < 0 ? 1 : cost;
    }

    public boolean hasPersonalLimit() {
        return personalLimit > 0;
    }
}
