package functions

import java.util.*

fun main(args: Array<String>) {
    val numbers: MutableList<Int> = mutableListOf(1, 6, 8)
    numbers[0] = 5
    numbers.add(2, 7)
    numbers.add(9)
    numbers.forEach { print("$it--") }
    numbers.swap(1, 2)
    numbers.swap(4, 0)
    println()
    numbers.forEach { print("$it--") }

    println()
    println()

    val string = "ABC"
    val uuid: UUID? = string.toUUID()
    println(uuid)
}

fun String.toUUID(): UUID? = try {
    UUID.fromString(this)
} catch (e: IllegalArgumentException) {
    null
}

fun <T> MutableList<T>.swap(x: Int, y: Int) {
    this[x] = this[y].also { this[y] = this[x] }
}