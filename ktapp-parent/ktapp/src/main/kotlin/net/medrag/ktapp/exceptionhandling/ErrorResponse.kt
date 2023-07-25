package net.medrag.ktapp.exceptionhandling

import org.springframework.boot.actuate.metrics.http.Outcome
import java.time.OffsetDateTime

/**
 * @author Stanislav Tretiakov
 * 17.07.2023
 */
data class ErrorResponse(
    val path: String = Outcome.UNKNOWN.toString(),
    val status: Int = 500,
    val message: String = "Error has happened during request processing.",
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
    val errorCode: String = "KTA-000"
)