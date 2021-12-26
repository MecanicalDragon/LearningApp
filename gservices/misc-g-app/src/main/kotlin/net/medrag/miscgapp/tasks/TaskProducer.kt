package net.medrag.miscgapp.tasks

import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

/**
 * Component, that produces tasks.
 * @author Stanislav Tretyakov
 * 17.12.2021
 */
@Service
class TaskProducer(val taskProcessor: TaskProcessor) {

    /**
     * Sign to continue producing.
     */
    private val produce = AtomicBoolean(true)

    /**
     * Map of active tasks. All tasks are collected here, if they started and removed from here, being finished.
     */
    private val activeTasks = ConcurrentHashMap<String, String>()

    /**
     * Chance to finish invalid task instead of existing one.
     */
    private val invalidTaskChance = 0.3

    @PostConstruct
    fun init() {
        startTaskCreatingThread()
        startTaskCompletingThread()
    }

    /**
     * Create separate thread, that will create tasks with some random interval.
     */
    fun startTaskCreatingThread() {
        Thread {
            while (produce.get()) {
                TimeUnit.MILLISECONDS.sleep((Math.random() * 5000).toLong())
                val taskId = UUID.randomUUID().toString()
                activeTasks.put(taskId, taskId)
                taskProcessor.startLongTask(Task(taskId))
            }
        }.apply {
            isDaemon = true
            start()
        }
    }

    /**
     * Create separate thread, that will finish tasks from [activeTasks] with some random interval. Also, it can finish
     * invalid task with chance of [invalidTaskChance].
     */
    fun startTaskCompletingThread() {
        Thread {
            TimeUnit.SECONDS.sleep((30).toLong())
            while (produce.get()) {
                val taskId = if (Math.random() > 0.7) UUID.randomUUID().toString() else {
                    activeTasks.keys.randomOrNull()?.also {
                        activeTasks.remove(it)
                    } ?: UUID.randomUUID().toString()
                }
                taskProcessor.completeTask(taskId)
                TimeUnit.MILLISECONDS.sleep((Math.random() * 5000).toLong())
            }
        }.apply {
            isDaemon = true
            start()
        }
    }

    @PreDestroy
    fun destroy() {
        produce.set(false)
    }
}
