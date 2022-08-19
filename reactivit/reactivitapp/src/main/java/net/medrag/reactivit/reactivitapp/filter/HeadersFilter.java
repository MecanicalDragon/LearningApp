package net.medrag.reactivit.reactivitapp.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class HeadersFilter implements WebFilter {

    public static final String REQUEST_ID = "rquid";
    public static final String NAME = "name";
    public static final String SNAME = "sname";
    public static final String SENIORITY = "seniority";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        final var requestIdOpt = getRequestId(exchange.getRequest().getHeaders());
        if (requestIdOpt.isPresent()) {
            final var requestId = requestIdOpt.get();
            exchange.getResponse().getHeaders().add(REQUEST_ID, requestId);
            return chain.filter(exchange).contextWrite(ctx -> ctx.put(REQUEST_ID, requestId));
        } else {
            return chain.filter(exchange);
        }
    }

    private Optional<String> getRequestId(HttpHeaders headers) {
        List<String> requestIdHeaders = headers.get(REQUEST_ID);
        return requestIdHeaders == null || requestIdHeaders.isEmpty()
            ? Optional.empty()
            : Optional.ofNullable(requestIdHeaders.get(0));
    }
}
