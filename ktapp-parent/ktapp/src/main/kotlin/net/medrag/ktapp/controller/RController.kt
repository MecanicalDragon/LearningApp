package net.medrag.ktapp.controller

import mu.KotlinLogging
import net.medrag.ktapp.context.ProcessContextParameter
import net.medrag.ktapp.context.withMdc
import net.medrag.ktapp.service.SuspendService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * @author Stanislav Tretiakov
 * 17.07.2023
 */
@RestController
class RController(
    private val suspendService: SuspendService
) {

    @GetMapping("/get/{value}")
    suspend fun processGet(@PathVariable value: String): String {
        return withMdc {
            log.info { "controller... $value" }
            suspendService.process(value)
        }
    }

    @GetMapping("/getit")
    suspend fun processGet(): String {
        return withMdc({ it.enrich(ProcessContextParameter.SOME_DATA, "QQQ") }) {
            log.info("get it...")
            UUID.randomUUID().toString()
        }
    }

    companion object {
        private val log = KotlinLogging.logger { }
    }
}