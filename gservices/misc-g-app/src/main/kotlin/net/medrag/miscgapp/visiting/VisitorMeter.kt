package net.medrag.miscgapp.visiting

import io.micrometer.core.instrument.DistributionSummary
import io.micrometer.core.instrument.Gauge
import io.micrometer.prometheus.PrometheusMeterRegistry
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

/**
 * Collects metrics of visitors amount, visitors, tagged by their names and age. Also collects metric of total visit time,
 * average visit time, every average age of visitors.
 *
 * @author Stanislav Tretyakov
 * 17.12.2021
 */
@Service
class VisitorMeter(private val meterRegistry: PrometheusMeterRegistry) {

    private val timer = meterRegistry.timer(VISITING_TIMER)

    private val averageVisitTime = Gauge.builder(AVERAGE_VISITING_TIME) { timer.takeSnapshot().mean() }
        .description("average visit time").register(meterRegistry)

    private val averageAge = DistributionSummary
        .builder(AVERAGE_VISITORS_AGE)
        .description("average age of visitors")
//        .publishPercentileHistogram()
        .baseUnit("age")
        .publishPercentiles(0.25, 0.5, 0.75)
        .register(meterRegistry)

    fun visit(visitor: Visitor) {
        timer.record {
            TimeUnit.SECONDS.sleep((Math.random() * 10).toLong())
            logger.info("Visitor <${visitor.name}> has visited metric collector.")
            val age = when (visitor.age) {
                in (1..20) -> "teenage"
                in (21..30) -> "young"
                in (31..50) -> "adult"
                in (51..100) -> "old"
                else -> "out of estimation".also {
                    logger.warn { "Visitor is out of estimation with age of $it." }
                }
            }
            averageAge.record(visitor.age.toDouble())
            meterRegistry.counter(VISITOR_NAMES, NAME, visitor.name).increment()
            meterRegistry.counter(VISITOR_AGES, AGE, age).increment()
        }
    }
}

const val VISITING_TIMER = "visiting_time"
const val AVERAGE_VISITING_TIME = "average_visiting_time"
const val AVERAGE_VISITORS_AGE = "average_visitors_age"

const val VISITOR_NAMES = "visitor_names"
const val VISITOR_AGES = "visitor_ages"
const val NAME = "name"
const val AGE = "age"

private val logger = KotlinLogging.logger { }
