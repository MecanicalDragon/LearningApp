package net.medrag.ktapp.exception

/**
 * This exception is thrown when data class non-null property can not be assigned due to nullability of the source data.
 */
class NonNullValueIsNullException(
    message: String
) : RuntimeException(message)