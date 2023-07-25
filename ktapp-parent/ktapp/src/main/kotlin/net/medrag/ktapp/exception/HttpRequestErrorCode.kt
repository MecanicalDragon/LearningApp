package net.medrag.ktapp.exception

import org.springframework.http.HttpStatus

/**
 * @author Stanislav Tretiakov
 * 17.07.2023
 */
class HttpRequestErrorCode(
    override val code: String,
    override val httpStatus: HttpStatus,
    override val message: String,
    override val alert: Boolean
): ErrorCode