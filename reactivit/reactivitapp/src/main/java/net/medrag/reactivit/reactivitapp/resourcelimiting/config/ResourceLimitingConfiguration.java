package net.medrag.reactivit.reactivitapp.resourcelimiting.config;

import lombok.extern.slf4j.Slf4j;
import net.medrag.reactivit.reactivitapp.resourcelimiting.ApplicationResource;
import net.medrag.reactivit.reactivitapp.resourcelimiting.ApplicationResourceManager;
import net.medrag.reactivit.reactivitapp.resourcelimiting.ApplicationResourcePool;
import net.medrag.reactivit.reactivitapp.resourcelimiting.ResourceCounter;
import net.medrag.reactivit.reactivitapp.resourcelimiting.providers.IsolatedResourceProvider;
import net.medrag.reactivit.reactivitapp.resourcelimiting.providers.MultiResourceProvider;
import net.medrag.reactivit.reactivitapp.resourcelimiting.providers.PoolBackedResourceProvider;
import net.medrag.reactivit.reactivitapp.resourcelimiting.providers.ResourceProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.*;

/**
 * @author Stanislav Tretyakov
 * 21.12.2022
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ResourceLimitingProperties.class)
public class ResourceLimitingConfiguration {

    /**
     * Global application resources pool.
     */
    @Bean
    public ApplicationResourcePool applicationResourcePool(ResourceLimitingProperties properties) {
        final var resourceCapacity = properties.getResourceCapacity();
        final var resources = new EnumMap<ApplicationResource, ResourceCounter>(ApplicationResource.class);
        resourceCapacity.forEach((resource, limit) -> resources.put(resource, new ResourceCounter(limit)));
        return new ApplicationResourcePool(Map.copyOf(resources));
    }

    @Bean
    public ApplicationResourceManager applicationResourceManager(
        ApplicationResourcePool applicationResourcePool,
        ResourceLimitingProperties resourceLimitingProperties
    ) {
        final var resourceMappings = resourceLimitingProperties.getEndpointLimits();
        final var tempMap = new HashMap<ApplicationResourceManager.Endpoint, ResourceProvider>();
        for (Map.Entry<String, Set<ResourceLimit>> mapping : resourceMappings.entrySet()) {
            final var methodAndPath = mapping.getKey().split(" ", 2);
            final var endpoint =
                new ApplicationResourceManager.Endpoint(HttpMethod.resolve(methodAndPath[0]), new PathPatternParser().parse(methodAndPath[1]));
            tempMap.put(endpoint, buildResourceProvider(applicationResourcePool, mapping.getValue()));
        }
        final var defaultProvider = buildResourceProvider(applicationResourcePool, resourceLimitingProperties.getDefaultConsumption());
        log.info("ResourceLimitingProperties:\n{}", resourceLimitingProperties);
        log.info("ApplicationResourcePool:\n{}", applicationResourcePool);
        log.info("Result mappings:\n{}", tempMap);
        log.info("Default ResourceProvider:\n{}", defaultProvider);
        return new ApplicationResourceManager(Map.copyOf(tempMap), defaultProvider);
    }

    private ResourceProvider buildResourceProvider(ApplicationResourcePool resourcePool, Set<ResourceLimit> limits) {
        if (limits.size() == 1) {
            return buildProviderForSingleResource(resourcePool, limits.iterator().next());
        } else {
            final var resourceLimits = new TreeSet<>(Comparator.<ResourceLimit>comparingInt(limit -> limit.getResource().ordinal()));
            resourceLimits.addAll(limits);
            List<ResourceProvider> providers = new ArrayList<>(resourceLimits.size());
            for (ResourceLimit resourceLimit : resourceLimits) {
                providers.add(buildProviderForSingleResource(resourcePool, resourceLimit));
            }
            return new MultiResourceProvider(providers);
        }
    }

    private ResourceProvider buildProviderForSingleResource(ApplicationResourcePool resourcePool, ResourceLimit limit) {
        if (resourcePool.resourceExists(limit.getResource())) {
            if (limit.hasPersonalLimit()) {
                final var counter = new ResourceCounter(limit.getPersonalLimit());
                final var isolatedProvider = new IsolatedResourceProvider(limit.getResource(), counter, limit.getCost());
                final var pooledProvider = new PoolBackedResourceProvider(resourcePool, limit.getResource(), limit.getCost());
                return new MultiResourceProvider(List.of(isolatedProvider, pooledProvider));
            } else {
                return new PoolBackedResourceProvider(resourcePool, limit.getResource(), limit.getCost());
            }
        } else {
            if (limit.hasPersonalLimit()) {
                return new IsolatedResourceProvider(limit.getResource(), new ResourceCounter(limit.getPersonalLimit()), limit.getCost());
            } else {
                throw new RuntimeException(
                    "Configuration is invalid: Resource <" + limit.getResource()
                        + "> limit doesn't exist neither in Global config, nor in specific one."
                );
            }
        }
    }
}
