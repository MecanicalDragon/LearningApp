package net.medrag.ktapp.monitoring

import io.micrometer.core.instrument.*
import net.medrag.ktapp.ext.applyIf
import java.time.Duration

private const val COUNTER_POSTFIX = "_counter"
private const val DURATION_POSTFIX = "_duration"

fun MeterRegistry.counter(metric: Metric, tags: Iterable<Tag>): Counter {
    return Counter.builder(metric.metricName + COUNTER_POSTFIX)
        .description(metric.description)
        .tags(tags)
        .register(this)
}

fun MeterRegistry.counter(metric: Metric, vararg tags: String): Counter =
    Counter.builder(metric.metricName + COUNTER_POSTFIX)
        .description(metric.description)
        .tags(*tags)
        .register(this)

fun counterName(metric: Metric): String = metric.metricName + COUNTER_POSTFIX

fun buildTimer(
    metricName: String,
    description: String,
    tags: Tags,
    config: MonitoringConfig,
    measuredParameter: String
): Timer.Builder {
    return Timer.builder(metricName)
        .tags(tags)
        .description(description)
        .applyIf(config.percentilesEnabled) {
            publishPercentiles(*config.percentiles)
        }
        .applyIf(config.sloEnabled) {
            val durations: Array<Duration> = config.customSlo[measuredParameter] ?: config.slo
            if (durations.isNotEmpty()) {
                maximumExpectedValue(durations[durations.size - 1])
                minimumExpectedValue(durations[0])
                serviceLevelObjectives(*durations)
            }
        }
}

fun MeterRegistry.timer(
    metric: Metric,
    tags: Tags,
    config: MonitoringConfig,
    measuredParameter: String
): Timer {
    return buildTimer(metric.metricName + DURATION_POSTFIX, metric.description, tags, config, measuredParameter)
        .register(this)
}