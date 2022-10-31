package net.medrag.reactivit.reactivitapp.retry;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

/**
 * Component to build and provide default external retry policy.
 *
 * @author Stanislav Tretyakov
 * 28.10.2022
 */
@Component
@RequiredArgsConstructor
public class CommonExternalCallsRetryPolicyProvider implements RetryPolicyProvider {

    private static final int REQUEST_TIMEOUT_CODE = HttpStatus.REQUEST_TIMEOUT.value();
    private static final int TOO_MANY_REQUESTS_CODE = HttpStatus.TOO_MANY_REQUESTS.value();

    private final CommonExternalCallsRetryProperties retryProperties;

    @Override
    public Retry retryPolicy() {
        return Retry.backoff(retryProperties.getAttempts(), retryProperties.getMinBackoff())
            .maxBackoff(retryProperties.getMaxBackoff())
            .jitter(retryProperties.getJitter())
            .filter(throwable ->
                throwable instanceof WebClientResponseException
                    && isStatusCodeRetryable(((WebClientResponseException) throwable).getStatusCode()
                )).onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> new RuntimeException("Http call failed."));
    }

    private boolean isStatusCodeRetryable(HttpStatus status) {
        return status.is5xxServerError() || status.value() == REQUEST_TIMEOUT_CODE || status.value() == TOO_MANY_REQUESTS_CODE;
    }
}
