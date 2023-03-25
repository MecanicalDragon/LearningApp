package net.medrag.reactivit.reactivitapp.resourcelimiting;

import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

/**
 * Filter that filters requests by configured {@link ApplicationResource}s.
 *
 * @author Stanislav Tretyakov
 * 21.12.2022
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ApplicationResourceFilter implements WebFilter {

    private final ApplicationResourceManager applicationResourceManager;

    @Override
    public @NonNull Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        final var request = exchange.getRequest();
        final var acquireResult = applicationResourceManager.acquireResourcesForRequest(request.getMethod(), request.getPath());
        if (acquireResult.isRequestAllowed()) {
            return chain.filter(exchange)
                .doFinally(signal -> acquireResult.getReleaseCallback().run());
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }
    }
}
