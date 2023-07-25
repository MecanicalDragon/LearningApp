package net.medrag.ktapp.filter

import net.medrag.ktapp.context.ProcessContext
import net.medrag.ktapp.context.ProcessContextParameter
import net.medrag.ktapp.context.addContext
import net.medrag.ktapp.ext.header
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.util.context.ContextView

/**
 * Filter extracts http headers from [ServerWebExchange] and puts it into the Reactor context as a part of [ProcessContext].
 * Later it can be retrieved from the [ContextView].
 */
@Component
class HeadersFilter : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val requestId: String? = exchange.header(RequestHeaders.REQUEST_ID)
        val requesterUid: String? = exchange.header(RequestHeaders.REQUESTER_UID)
        val someData: String? = exchange.header(RequestHeaders.SOME_DATA)

        var processContext = ProcessContext.of(
            ProcessContextParameter.REQUEST_HEADERS to mapOf(
                RequestHeaders.REQUEST_ID to requestId,
                RequestHeaders.REQUESTER_UID to requesterUid,
                RequestHeaders.SOME_DATA to someData,
            )
        )
        if (!requestId.isNullOrEmpty()) {
            exchange.response.headers.add(RequestHeaders.REQUEST_ID, requestId)
            processContext = processContext.enrich(ProcessContextParameter.REQUEST_ID, requestId)
        }
        if (!requesterUid.isNullOrEmpty()) {
            exchange.response.headers.add(RequestHeaders.REQUESTER_UID, requesterUid)
            processContext = processContext.enrich(ProcessContextParameter.REQUESTER_UID, requesterUid)
        }
        if (!someData.isNullOrEmpty()) {
            exchange.response.headers.add(RequestHeaders.SOME_DATA, someData)
            processContext = processContext.enrich(ProcessContextParameter.SOME_DATA, someData)
        }

        println(processContext)
        return chain
            .filter(exchange)
            .addContext(processContext)
    }
}