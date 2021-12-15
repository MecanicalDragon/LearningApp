package net.medrag.algtasks

/**
 * Purpose of this task is to find how many days will pass to hotter day for each day in an array of day temperatures.
 */
private val tempByDays = arrayOf(13, 12, 15, 11, 9, 12, 16)

/**
 * Works for O(n^2) of Runtime Complexity
 * Works for O(n) or O(1) of Memory Complexity
 * @param temperatures - array of temperatures by days.
 */
fun temperaturesForN2(temperatures: Array<Int>) {
    val answers = IntArray(temperatures.size)
    for (i in 0 until temperatures.size) {
        for (j in (i + 1) until temperatures.size) {
            if (temperatures[j] > temperatures[i]) {
                answers[i] = j - i
                break
            }
        }
    }
    println(answers.contentToString())
}

/**
 * Works for O(n) of Runtime Complexity, cause every element in the stack can be added or removed only once.
 * Works for O(n) of Memory Complexity
 * @param temperatures - array of temperatures by days.
 */
fun temperaturesForN(temperatures: Array<Int>) {
    val answers = IntArray(temperatures.size)

    val stack = ArrayDeque<Pair<Int, Int>>()
    for (i in temperatures.size - 1 downTo 0) {
        while (!stack.isEmpty() && stack.last().first <= temperatures[i]) {
            stack.removeLast()
        }
        if (!stack.isEmpty()) {
            answers[i] = stack.last().second - i
        }
        stack.add(Pair(temperatures[i], i))
    }
    println(answers.contentToString())
}

fun main(args: Array<String>) {
    temperaturesForN2(tempByDays)
    temperaturesForN(tempByDays)
}
