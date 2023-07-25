package net.medrag.ktapp.exception

import org.springframework.http.HttpStatus

interface ErrorCode {
    val code: String
    val httpStatus: HttpStatus
    val message: String
    val alert: Boolean
}
