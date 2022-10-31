package net.medrag.reactivit.reactivitapp.retry;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Duration;

/**
 * @author Stanislav Tretyakov
 * 28.10.2022
 */
@Getter
@ToString
@ConstructorBinding
@ConfigurationProperties("reactivit.external-calls-retry-policy")
public class CommonExternalCallsRetryProperties {

    private static final int DEFAULT_ATTEMPTS_NUMBER = 3;
    private static final long DEFAULT_MIN_BACKOFF = 500;
    private static final long DEFAULT_MAX_BACKOFF = 5000;
    private static final double DEFAULT_JITTER = 0.5;

    /**
     * Max number of attempts before fail.
     */
    private final int attempts;

    /**
     * Min backoff in ms between attempts.
     */
    private final Duration minBackoff;

    /**
     * Max backoff in ms between attempts.
     */
    private final Duration maxBackoff;

    /**
     * Jitter of retries.
     */
    private final double jitter;

    public CommonExternalCallsRetryProperties(Integer attempts, Long minBackoff, Long maxBackoff, Double jitter) {
        this.attempts = attempts == null ? DEFAULT_ATTEMPTS_NUMBER : attempts;
        this.minBackoff = Duration.ofMillis(minBackoff == null ? DEFAULT_MIN_BACKOFF : minBackoff);
        this.maxBackoff = Duration.ofMillis(maxBackoff == null ? DEFAULT_MAX_BACKOFF : maxBackoff);
        this.jitter = jitter == null ? DEFAULT_JITTER : jitter;
    }
}
