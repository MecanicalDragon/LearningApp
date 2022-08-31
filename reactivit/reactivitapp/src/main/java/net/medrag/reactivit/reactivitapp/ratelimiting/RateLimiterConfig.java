package net.medrag.reactivit.reactivitapp.ratelimiting;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Stanislav Tretyakov
 * 30.08.2022
 */
@Configuration
public class RateLimiterConfig {

    private static final String RATE_LIMITING_GROUP_GLOBAL = "global";

    @Bean
    public RateLimiter globalRateLimiter(
        RateLimiterRegistry rateLimiterRegistry
    ) {
        final var rateLimiter = rateLimiterRegistry.rateLimiter(RATE_LIMITING_GROUP_GLOBAL);
        rateLimiter.getEventPublisher()
            .onFailure(event -> System.out.println("fail: " + event))
            .onSuccess(event -> System.out.println("success: " + event));
        return rateLimiter;
    }
}
