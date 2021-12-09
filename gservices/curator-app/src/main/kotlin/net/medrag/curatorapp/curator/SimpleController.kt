package net.medrag.curatorapp.curator

import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex
import org.apache.curator.framework.recipes.shared.SharedCount
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Stanislav Tretyakov
 * 14.11.2021
 */
@RestController
class SimpleController(
    private val curator: CuratorFramework
) {

    // distributed lock example
    private val sharedLock = InterProcessSemaphoreMutex(curator, "/mutex/process/simple")

    // distributed counter example
    private val counter = SharedCount(curator, "/counters/simple", 0)

    init {
        counter.start()
    }

    /**
     * Set value to Node
     */
    @PostMapping("/zprop/{name}/{value}")
    fun addProp(@PathVariable name: String, @PathVariable value: String): String {

        sharedLock.acquire()
        try {
            curator.checkExists().forPath("/$name") ?: curator.create().forPath("/$name")
            curator.setData().forPath("/$name", value.toByteArray())
            do {
                val cntr = counter.versionedValue
                println("current value:  ${cntr.value}")
                println("current version:  ${cntr.version}")
            } while (!counter.trySetCount(cntr, cntr.value + 1))
            return "OK"
        } finally {
            sharedLock.release()
        }
    }

    /**
     * Get value from Node
     */
    @GetMapping("/zprop/{name}")
    fun getProp(@PathVariable name: String): String {
        return String(curator.data.forPath("/$name"))
    }
}
