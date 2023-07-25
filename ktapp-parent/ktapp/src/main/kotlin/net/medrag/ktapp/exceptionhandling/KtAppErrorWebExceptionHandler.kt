package net.medrag.ktapp.exceptionhandling

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono
import kotlin.reflect.full.isSubclassOf

@Component
class KtAppErrorWebExceptionHandler(
    errorAttributes: ErrorAttributes,
    applicationContext: ApplicationContext,
    serverCodecConfigurer: ServerCodecConfigurer,
    errorRenderers: List<ErrorRenderer<out Throwable>>
) : AbstractErrorWebExceptionHandler(errorAttributes, WebProperties.Resources(), applicationContext) {
    private val renderers = errorRenderers.associateBy { it.supportedClass }

    init {
        super.setMessageWriters(serverCodecConfigurer.writers)
        super.setMessageReaders(serverCodecConfigurer.readers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes): RouterFunction<ServerResponse> {
        return RouterFunctions.route(RequestPredicates.all()) { request: ServerRequest -> renderErrorResponse(request) }
    }

    private fun renderErrorResponse(request: ServerRequest): Mono<ServerResponse> {
        val path = request.requestPath().pathWithinApplication().value()
        val responseData = with(getError(request)) {
            renderers[this::class]?.renderResponse(this, path) ?: run {
                for (renderer in renderers) {
                    if (this::class.isSubclassOf(renderer.key)) {
                        return@run renderer.value.renderResponse(this, path)
                    }
                }
                ErrorResponse(path = path)
            }
        }
        return ServerResponse.status(responseData.status).body(BodyInserters.fromValue(responseData))
    }

    companion object {
        private val log = KotlinLogging.logger { }
    }
}