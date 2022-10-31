package net.medrag.reactivit.reactivitapp.retry;

import reactor.util.retry.Retry;

/**
 * API to obtain common external calls retry policy.
 *
 * @author Stanislav Tretyakov
 * 28.10.2022
 */
public interface RetryPolicyProvider {
    Retry retryPolicy();
}
