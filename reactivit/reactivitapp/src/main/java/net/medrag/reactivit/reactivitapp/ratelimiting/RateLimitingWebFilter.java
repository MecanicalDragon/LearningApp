package net.medrag.reactivit.reactivitapp.ratelimiting;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 30.08.2022
 */
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RateLimitingWebFilter implements WebFilter {

    private static final List<PathPattern> exclusions = List.of(PathPatternParser.defaultInstance.parse("/actuator/**"));

    private final RateLimiter globalRateLimiter;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        RequestPath path = serverWebExchange.getRequest().getPath();
        for (PathPattern pathPattern : exclusions) {
            if (pathPattern.matches(path.pathWithinApplication())) {
                return webFilterChain.filter(serverWebExchange);
            }
        }
        return webFilterChain.filter(serverWebExchange)
            .transformDeferred(RateLimiterOperator.of(globalRateLimiter));
    }
}
