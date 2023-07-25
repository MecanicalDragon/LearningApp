package net.medrag.ktapp.config

import io.netty.handler.codec.http.HttpHeaders
import net.medrag.ktapp.context.ProcessContextParameter
import net.medrag.ktapp.context.processContext
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import reactor.util.context.ContextView

/**
 * @author Stanislav Tretiakov
 * 17.07.2023
 */
@Configuration
@EnableConfigurationProperties(IncomingHttpRequestsMonitoringProperties::class)
class KtAppConfiguration {

    @Bean
    fun webClient(): WebClient {
        val httpClient = HttpClient.create()
            .headersWhen { httpHeaders: HttpHeaders ->
                Mono.deferContextual { contextView: ContextView ->
                    contextView.processContext().get<Map<String, String>>(ProcessContextParameter.REQUEST_HEADERS)
                        ?.let { it.forEach { (k, v) -> httpHeaders.add(k, v) } }
                    Mono.just(httpHeaders)
                }
            }
        return WebClient.builder().clientConnector(ReactorClientHttpConnector(httpClient)).build()
    }
}