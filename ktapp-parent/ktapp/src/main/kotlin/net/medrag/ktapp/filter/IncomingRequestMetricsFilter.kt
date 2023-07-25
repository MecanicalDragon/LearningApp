package net.medrag.ktapp.filter

import io.micrometer.core.instrument.MeterRegistry
import net.medrag.ktapp.config.IncomingHttpRequestsMonitoringProperties
import net.medrag.ktapp.ext.extractUri
import net.medrag.ktapp.monitoring.HttpRequestData
import net.medrag.ktapp.monitoring.observation
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnProperty(name = ["ktapp.monitoring.incoming-requests.config.enabled"], havingValue = "true", matchIfMissing = true)
class IncomingRequestMetricsFilter(
    private val meterRegistry: MeterRegistry,
    private val monitoringConfig: IncomingHttpRequestsMonitoringProperties
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return chain.filter(exchange).tap(
            observation(
                meterRegistry,
                monitoringConfig.config,
                HttpRequestData(exchange.request.method.name(), { exchange.extractUri() }, { exchange.response.statusCode?.value() })
            )
        )
    }
}
