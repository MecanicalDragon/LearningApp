package net.medrag.ktapp.monitoring

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tags
import io.micrometer.core.instrument.binder.http.Outcome
import mu.KotlinLogging
import net.medrag.ktapp.exception.CodedException
import net.medrag.ktapp.exception.ErrorCode
import org.reactivestreams.Publisher
import reactor.core.observability.SignalListener
import reactor.core.observability.SignalListenerFactory
import reactor.core.publisher.SignalType
import reactor.util.context.ContextView
import java.time.Duration
import java.util.function.Supplier

private const val NONE = "None"
private val log = KotlinLogging.logger { }

internal fun <T> observation(meterRegistry: MeterRegistry, monitoringConfig: MonitoringConfig, requestData: HttpRequestData) =
    RequestMetricsFactory<T>(meterRegistry, monitoringConfig, requestData)

internal data class HttpRequestData(
    val method: String,
    private val uriSupplier: Supplier<String>,
    private val statusCodeSupplier: Supplier<Int?>
) {
    val statusCode: Int?
        get() = statusCodeSupplier.get()
    val uri: String
        get() = uriSupplier.get()
}

internal class RequestMetricsFactory<T>(
    private val meterRegistry: MeterRegistry,
    private val monitoringConfig: MonitoringConfig,
    private val requestData: HttpRequestData
) : SignalListenerFactory<T, HttpRequestData> {

    override fun initializePublisherState(source: Publisher<out T>): HttpRequestData {
        return requestData
    }

    override fun createListener(
        source: Publisher<out T>?,
        listenerContext: ContextView?,
        publisherContext: HttpRequestData
    ): SignalListener<T> {
        return HttpRequestListener(meterRegistry, monitoringConfig, publisherContext)
    }
}

internal class HttpRequestListener<T>(
    private val meterRegistry: MeterRegistry,
    private val monitoringConfig: MonitoringConfig,
    private val httpRequestData: HttpRequestData,
    private val start: Long = System.nanoTime()
) : SignalListener<T> {

    override fun doAfterComplete() {
        fillMetrics(null)
    }

    override fun doAfterError(error: Throwable?) {
        fillMetrics(error)
    }

    override fun doOnCancel() {
        fillMetrics(null)
    }

    override fun doFirst() {}

    override fun doFinally(terminationType: SignalType?) {}

    override fun doOnSubscription() {}

    override fun doOnFusion(negotiatedFusion: Int) {}

    override fun doOnRequest(requested: Long) {}

    override fun doOnComplete() {}

    override fun doOnError(error: Throwable?) {}

    override fun doOnMalformedOnError(error: Throwable?) {}

    override fun doOnMalformedOnComplete() {}

    override fun doOnMalformedOnNext(value: T) {}

    override fun doOnNext(value: T) {}

    override fun handleListenerError(listenerError: Throwable?) {
        log.warn(listenerError) { "Error has happened during http request duration metrics filling job." }
    }

    private fun fillMetrics(error: Throwable?) {
        val uri = httpRequestData.uri
        val errorCode: ErrorCode? = (error as? CodedException)?.errorCode
        val statusCode: Int? = errorCode?.httpStatus?.value() ?: httpRequestData.statusCode
        val tags = Tags.of("method", httpRequestData.method)
            .and("uri", uri)
            .and("status", errorCode?.httpStatus?.value()?.toString() ?: statusCode?.toString() ?: Outcome.UNKNOWN.name)
            .and(Outcome.forStatus(statusCode ?: 0).asTag())
            .and("error", error?.let { it::class.simpleName } ?: NONE)
            .and("error_code", errorCode?.code ?: NONE)
            .and("alert", if (errorCode?.alert == true) "true" else NONE)

        meterRegistry.counter(IncomingHttpRequestsMetric.INCOMING_HTTP_REQUESTS, tags)
            .increment()
        meterRegistry.timer(
            IncomingHttpRequestsMetric.INCOMING_HTTP_REQUESTS,
            tags,
            monitoringConfig,
            uri
        ).record(Duration.ofNanos(System.nanoTime() - start))
    }
}
