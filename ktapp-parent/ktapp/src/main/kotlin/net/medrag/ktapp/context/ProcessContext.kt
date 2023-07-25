package net.medrag.ktapp.context

import kotlinx.coroutines.slf4j.MDCContext
import org.slf4j.MDC
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * Context of the executed business process.
 * Keeps metadata for a process, stored under the [ProcessContextParameter] keys.
 */
class ProcessContext private constructor(
    private val values: Map<ProcessContextParameter, Any?>
) : AbstractCoroutineContextElement(Key) {

    /**
     * Key of [ProcessContext].
     * This key allows to [ProcessContext] to be stored in the [CoroutineContext] and retrieved by it.
     */
    object Key : CoroutineContext.Key<ProcessContext>

    fun enrich(key: ProcessContextParameter, value: Any?): ProcessContext {
        val context = HashMap(this.values)
        context[key] = value
        return ProcessContext(context)
    }

    operator fun <T> get(key: ProcessContextParameter): T? = values[key] as? T?

    /**
     * Add context values to MDC and create a [CoroutineContext] for a chain of downstream suspended functions.
     * MDC values will be automatically added to all log statements within this [CoroutineContext].
     */
    fun withMdc(): CoroutineContext {
        MDC.clear()
        values.forEach { (key, value) ->
            if (key.loggable && value != null) {
                MDC.put(key.parameterName, value.toString())
            }
        }
        return this + MDCContext()
    }

    override fun toString(): String {
        return "FlowContext: $values"
    }

    companion object {
        private val EMPTY_CONTEXT = ProcessContext(emptyMap())
        fun empty(): ProcessContext = EMPTY_CONTEXT
        fun of(vararg parameters: Pair<ProcessContextParameter, Any?>) = ProcessContext(mapOf(*parameters))
    }
}

/**
 * Key of the data stored in the [ProcessContext].
 * Any data stored in it can be stashed or retrieved only with this key.
 */
enum class ProcessContextParameter(
    val parameterName: String,
    val loggable: Boolean
) {
    REQUEST_ID("requestId", true),
    REQUESTER_UID("requesterUid", true),
    SOME_DATA("someData", true),
    REQUEST_HEADERS("headers", false)
}

