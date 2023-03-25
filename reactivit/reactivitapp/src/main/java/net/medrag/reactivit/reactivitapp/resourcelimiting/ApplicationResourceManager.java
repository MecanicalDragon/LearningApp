package net.medrag.reactivit.reactivitapp.resourcelimiting;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import net.medrag.reactivit.reactivitapp.resourcelimiting.providers.ResourceProvider;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import reactor.util.annotation.NonNull;

import java.util.Map;

/**
 * High-level abstraction to work with resources.
 * Supposed to be used to acquire resources for request.
 *
 * @author Stanislav Tretyakov
 * 22.12.2022
 */
@Slf4j
@RequiredArgsConstructor
public class ApplicationResourceManager {

    private final Map<Endpoint, ResourceProvider> limitedEndpoints;
    private final ResourceProvider defaultConsumption;

    /**
     * Acquire resources for request.
     *
     * @param httpMethod HTTP method
     * @param path       URL path
     * @return decision if request can be processed with a callback that must be executed after request is complete.
     */
    public AcquiringResult acquireResourcesForRequest(HttpMethod httpMethod, PathContainer path) {
        for (Map.Entry<Endpoint, ResourceProvider> entry : limitedEndpoints.entrySet()) {
            if (entry.getKey().isHit(httpMethod, path)) {
                return getAcquiringResult(entry.getValue());
            }
        }
        return getAcquiringResult(defaultConsumption);
    }

    @NonNull
    private AcquiringResult getAcquiringResult(ResourceProvider resourceProvider) {
        if (resourceProvider.tryToAcquireResources()) {
            log.debug("Resources have been acquired: <{}>.", resourceProvider);
            return new AcquiringResult(true, () -> {
                resourceProvider.releaseResources();
                log.debug("Resources have been released: <{}>.", resourceProvider);
            });
        } else {
            log.info("Request denied due to resource lack: <{}>.", resourceProvider);
            return new AcquiringResult(false, null);
        }
    }

    /**
     * Representation of a decision if request can be executed or not.
     */
    @Value
    public static class AcquiringResult {
        boolean requestAllowed;
        Runnable releaseCallback;
    }

    /**
     * Representation of application endpoint.
     * Defined with combination of Http method and path.
     */
    @Value
    public static class Endpoint {
        HttpMethod method;
        PathPattern path;

        private boolean isHit(HttpMethod httpMethod, PathContainer path) {
            return method == httpMethod && this.path.matches(path);
        }
    }
}
