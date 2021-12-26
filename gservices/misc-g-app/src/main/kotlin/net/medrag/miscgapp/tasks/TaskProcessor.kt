package net.medrag.miscgapp.tasks

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.LongTaskTimer
import io.micrometer.core.instrument.Timer
import io.micrometer.prometheus.PrometheusMeterRegistry
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

const val TASKS_ACTIVE = "tasks_active"
const val TASKS_STARTED = "tasks_started"
const val TASKS = "tasks"
const val TASKS_SUCCESSFUL = "tasks_successful"
const val TASKS_INVALID = "tasks_invalid"
const val TASKS_TOTAL_TIME = "tasks_total_time"
const val TASKS_COMPLETION_TIME = "tasks_completion_time"

/**
 * Long-term tasks processor.
 * @author Stanislav Tretyakov
 * 09.12.2021
 */
@Service
class TaskProcessor(private val meterRegistry: PrometheusMeterRegistry) {

    /**
     * Map of active task names and treir timers.
     */
    private val tasks = ConcurrentHashMap<String, LongTaskTimer.Sample>()

    /**
     * Metric of active tasks number collector.
     */
    private val activeTasksNumber = Gauge.builder(TASKS_ACTIVE, tasks) { tasks.size.toDouble() }
        .description("number of active tasks").tags(TASKS, "active").register(meterRegistry)

    /**
     * Metric of total started tasks number collector.
     */
    private val startedTasksCounter = Counter
        .builder(TASKS_STARTED)
        .description("total number of started tasks")
        .tags(TASKS, "started")
        .register(meterRegistry)

    /**
     * Metric of successful tasks number collector.
     */
    private val successCounter = Counter
        .builder(TASKS_SUCCESSFUL)
        .description("number of completed tasks")
        .tags(TASKS, "succeed")
        .register(meterRegistry)

    /**
     * Metric of invalid tasks number collector.
     */
    private val invalidCounter = Counter
        .builder(TASKS_INVALID)
        .description("number of invalid tasks")
        .tags(TASKS, "invalid")
        .register(meterRegistry)

    /**
     * Task completion method timer.
     */
    private val timer = Timer.builder(TASKS_COMPLETION_TIME)
        .description("time of task completion job").register(meterRegistry)

    /**
     * Creator of long-term timers.
     */
    private val longTaskTimer = LongTaskTimer.builder(TASKS_TOTAL_TIME)
        .description("time of long-term task job").register(meterRegistry)

    /**
     * Launch long-term task and store it in the map.
     * @param data Long-term task to launch.
     */
    fun startLongTask(data: Task) {

        val task = longTaskTimer.start()
        tasks[data.id] = task

        logger.info("Task <$data> has been started.")
        startedTasksCounter.increment()
    }

    /**
     * Retrieve task timer from map by task's id and stop it.
     * @param name String - task's name.
     * @return String - message.
     */
    fun completeTask(name: String): String {
        return timer.record<String> {
            Thread.sleep((Math.random() * 300).toLong())
            tasks.remove(name)?.let {
                logger.info("Task <$name> has been finished.")
                successCounter.increment()
                "Task <$name> last ${it.duration(TimeUnit.SECONDS)} (${it.stop()}) seconds."
            } ?: run {
                logger.warn("Task <$name> is invalid.")
                invalidCounter.increment()
                "No data for name $name."
            }
        } ?: throw IllegalStateException("Thing that should not be.")
    }
}

private val logger = KotlinLogging.logger { }
