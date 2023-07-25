package net.medrag.ktapp.service

import kotlinx.coroutines.reactor.awaitSingle
import mu.KotlinLogging
import net.medrag.ktapp.exception.CodedException
import net.medrag.ktapp.exception.HttpRequestErrorCode
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

/**
 * @author Stanislav Tretiakov
 * 17.07.2023
 */
@Service
class SuspendService(
    private val webClient: WebClient
) {
    suspend fun process(value: String): String {
        log.info { "processing... $value" }
        if (value == "ex") {
            val errorCode = HttpRequestErrorCode("KTA-001", HttpStatus.SERVICE_UNAVAILABLE, "oops", true)
            throw CodedException(errorCode)
        }
        try {
            val resp: String = webClient.get().uri("http://localhost:8080/getit").retrieve().bodyToMono(String::class.java).awaitSingle()
            log.info { "response: $resp" }
        } catch (e: Exception) {
            log.info { "exception: $e" }
        }
        return value.uppercase()
    }

    companion object {
        private val log = KotlinLogging.logger { }
    }
}