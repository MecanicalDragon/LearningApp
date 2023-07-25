package net.medrag.ktapp.exceptionhandling

import net.medrag.ktapp.exception.CodedException
import org.springframework.stereotype.Component

@Component
class CodedExceptionRenderer : ErrorRenderer<CodedException> {
    override val supportedClass = CodedException::class

    override fun renderResponseInner(error: CodedException, path: String): ErrorResponse {
        val errorCode = error.errorCode
        return ErrorResponse(
            path = path,
            status = errorCode.httpStatus.value(),
            message = errorCode.message,
            errorCode = errorCode.code
        )
    }
}