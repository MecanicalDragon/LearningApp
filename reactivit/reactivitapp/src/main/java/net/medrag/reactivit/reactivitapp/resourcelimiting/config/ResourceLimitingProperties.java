package net.medrag.reactivit.reactivitapp.resourcelimiting.config;

import lombok.Value;
import net.medrag.reactivit.reactivitapp.resourcelimiting.ApplicationResource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Properties for resource-based request limiting.
 *
 * @author Stanislav Tretyakov
 * 21.12.2022
 */
@Value
@ConstructorBinding
@ConfigurationProperties(prefix = "app.resource-limiting")
public class ResourceLimitingProperties {

    /**
     * Application resources and their application-level limits.
     * If specified, these resources will be counted for all endpoints that use this specific resource.
     * These limits are shared among all endpoints. Same-named {@link ApplicationResource}s from {@link #endpointLimits} are the same
     * resources, but limited for specific endpoint.
     */
    Map<ApplicationResource, Integer> resourceCapacity;

    /**
     * Application endpoints and their specific resource limits.
     * If specified, these limits will be counted for corresponding endpoint only.
     * Same-named {@link ApplicationResource}s from {@link #resourceCapacity} are the same resources, but their limits are application-level
     * and counted for the whole application.
     */
    Map<String, Set<ResourceLimit>> endpointLimits;

    /**
     * Default resource consumption.
     * If endpoint is not specified in {@link #endpointLimits} these resources will be consumed. May be empty; in this case all
     * not mentioned in {@link #endpointLimits} endpoints don't consume any resources at all.
     */
    Set<ResourceLimit> defaultConsumption;

    public ResourceLimitingProperties(
        Map<ApplicationResource, Integer> resourceCapacity,
        Map<String, Set<ResourceLimit>> endpointLimits,
        Set<ResourceLimit> defaultConsumption
    ) {
        this.resourceCapacity = resourceCapacity == null ? Collections.emptyMap() : resourceCapacity;
        this.endpointLimits = endpointLimits == null ? Collections.emptyMap() : endpointLimits;
        this.defaultConsumption = defaultConsumption == null ? Collections.emptySet() : defaultConsumption;
    }
}
