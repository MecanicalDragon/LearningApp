package net.medrag.ktapp.context

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.reactor.ReactorContext
import kotlinx.coroutines.withContext
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.context.ContextView
import kotlin.coroutines.CoroutineContext

/**
 * Key that keeps [ProcessContext] in the Reactor [ContextView].
 */
const val REACTOR_MDC_CONTEXT_KEY = "net.medrag.ktapp.context.MdcContext"

/**
 * Add [ProcessContext] to the Reactor [ContextView] under the key [REACTOR_MDC_CONTEXT_KEY].
 */
fun <T> Mono<T>.addContext(context: ProcessContext): Mono<T> =
    this.contextWrite { ctx -> ctx.put(REACTOR_MDC_CONTEXT_KEY, context) }

/**
 * Add [ProcessContext] to the Reactor [ContextView] under the key [REACTOR_MDC_CONTEXT_KEY].
 */
fun <T> Flux<T>.addContext(context: ProcessContext): Flux<T> =
    this.contextWrite { ctx -> ctx.put(REACTOR_MDC_CONTEXT_KEY, context) }

/**
 * Retrieve saved [ProcessContext] from the reactor [ContextView].
 */
fun ContextView.processContext(): ProcessContext = getOrDefault(REACTOR_MDC_CONTEXT_KEY, ProcessContext.empty()) ?: ProcessContext.empty()

/**
 * Get [ProcessContext] saved in the current [CoroutineContext].
 * First, checks if [ProcessContext] exists under the [ProcessContext.Key] and returns it if it exists.
 * If it isn't, tries to retrieve context from reactor context [ReactorContext.Key] using [REACTOR_MDC_CONTEXT_KEY].
 * If there is no context under both of these keys return new empty [ProcessContext].
 */
suspend inline fun processContext(): ProcessContext = with(currentCoroutineContext()) {
    this[ProcessContext.Key] ?: this[ReactorContext.Key]?.context?.processContext() ?: ProcessContext.empty()
}

/**
 * Execute code block with MDC context filled from the saved in current [CoroutineContext] [ProcessContext].
 * Subsequent executions will automatically save actual MDC context.
 */
suspend inline fun <R> withMdc(crossinline block: suspend CoroutineScope.() -> R): R {
    return withContext(processContext().withMdc()) {
        block.invoke(this)
    }
}

/**
 * Enrich [ProcessContext] from current [CoroutineContext] with specified function and execute a code block with filled with it MDC context.
 * Subsequent executions will automatically save actual MDC context.
 */
suspend inline fun <R> withMdc(contextEnrich: (ProcessContext) -> ProcessContext, crossinline block: suspend CoroutineScope.() -> R): R {
    return withContext(contextEnrich(processContext()).withMdc()) {
        block.invoke(this)
    }
}