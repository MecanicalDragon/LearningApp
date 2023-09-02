package net.medrag.ktapp.ext

import net.medrag.ktapp.exception.NonNullValueIsNullException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract


@OptIn(ExperimentalContracts::class)
fun <T> T?.nonNull(message: String = "Nullable value was provided where non-null value must be."): T {
    contract {
        returnsNotNull()
    }
    return this ?: throw NonNullValueIsNullException(message)
}

inline fun <T> T.applyIf(condition: Boolean, block: T.() -> Unit): T {
    if (condition) {
        block.invoke(this)
    }
    return this
}

inline fun String?.ifNotNullOrBlank(block: (String) -> Unit): String? {
    if (!this.isNullOrBlank()) {
        block.invoke(this)
    }
    return this
}

inline fun String?.ifNullOrBlank(block: () -> String): String {
    return if (this.isNullOrBlank()) block.invoke() else this
}