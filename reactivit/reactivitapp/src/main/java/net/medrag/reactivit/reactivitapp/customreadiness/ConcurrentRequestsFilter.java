package net.medrag.reactivit.reactivitapp.customreadiness;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author Stanislav Tretyakov
 * 31.08.2022
 */
@Component
@RequiredArgsConstructor
public class ConcurrentRequestsFilter implements WebFilter {

    private final ConcurrentRequestsNumberHealthIndicator readinessIndicator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        readinessIndicator.updateReadinessInc();
        return chain
            .filter(exchange)
            .doAfterTerminate(readinessIndicator::updateReadinessDec);
    }
}
