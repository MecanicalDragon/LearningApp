package net.medrag.ktapp.exceptionhandling

import kotlin.reflect.KClass

interface ErrorRenderer<T : Throwable> {
    val supportedClass: KClass<T>
    fun renderResponseInner(error: T, path: String): ErrorResponse
    fun renderResponse(error: Throwable, path: String): ErrorResponse {
        return (error as? T)?.let { renderResponseInner(it, path) } ?: ErrorResponse(path = path)
    }
}