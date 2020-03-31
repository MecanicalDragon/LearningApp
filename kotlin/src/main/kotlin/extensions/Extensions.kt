package extensions

import java.lang.IllegalArgumentException
import java.util.*

fun String.toUUID(): UUID? = try {
    UUID.fromString(this)
} catch (e: IllegalArgumentException) {
    null
}

fun <T> MutableList<T>.swap(x: Int, y: Int) {
    this[x] = this[y].also { this[y] = this[x] }
}