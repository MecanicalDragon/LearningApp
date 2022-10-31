package net.medrag.reactivit.reactivitapp.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

/**
 * @author Stanislav Tretyakov
 * 31.10.2022
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(CommonExternalCallsRetryProperties.class)
public class RetryConfiguration {

    @Bean
    public ExchangeFilterFunction retryExchangeFilterFunction(RetryPolicyProvider retryPolicyProvider) {
        return (request, exchange) -> exchange.exchange(request)
            .flatMap(clientResponse -> clientResponse.statusCode().isError()
                ? clientResponse.createException().flatMap(error -> {
                    log.warn("request failed: " + request + " <-> " + clientResponse);
                    return Mono.error(error);
                })
                : Mono.just(clientResponse)
            ).retryWhen(retryPolicyProvider.retryPolicy());
    }

    @Bean
    public WebClient webClient(RetryPolicyProvider retryPolicyProvider) {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("myConnectionPool")
            .maxConnections(2000)
            .pendingAcquireMaxCount(5000)
            .build();
        return WebClient.builder()
            .filter(retryExchangeFilterFunction(retryPolicyProvider))
            .clientConnector(new ReactorClientHttpConnector(HttpClient.create(connectionProvider)))
            .build();
    }
}
