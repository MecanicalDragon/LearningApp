package net.medrag.algtasks

/**
 * In this task we have a field of cells and a point on it with coordinates(m,n).
 * Also, we have a robot, that can move over this field, but only in top or right directions.
 * Initially, robot stands in cell with coordinates(0,0).
 * This task is about to find number of unique paths which robot can reach the point.
 */

/**
 * Solution implies that for every cell(m:n) following is true:
 * - paths(m,n) = paths(m-1,n) + paths(m,n-1)
 * - paths(x,0) = paths(0,x) = 1
 */

/**
 * Runtime Complexity is O(2^n+m)
 */
fun pathsToCoordinates(m: Int, n: Int): Int {
    if (m == 0 || n == 0) return 1
    plainComputations++
    return pathsToCoordinates(m - 1, n) + pathsToCoordinates(m, n - 1)
}

/**
 * Since we cache once computed cells runtime complexity is O(n*m)
 */
fun cachedPaths(m: Int, n: Int, cache: Array<IntArray>): Int {
    if (m == 0 || n == 0) return 1
    if (cache[m - 1][n - 1] == 0) {
        cache[m - 1][n - 1] = cachedPaths(m - 1, n, cache) + cachedPaths(m, n - 1, cache)
        computations++
    }
    return cache[m - 1][n - 1]
}

fun main(args: Array<String>) {
    val m = 5
    val n = 5
    println("Number of paths for O(2^n+m) is ${pathsToCoordinates(m, n)}, computations = $plainComputations}.")
    println(
        "Number of paths for O(n*m) is ${cachedPaths(m, n, Array(m) { IntArray(n) })}, computations = $computations}."
    )
    val x = (Math.random() * 100).toInt() - 50
    val y = (Math.random() * 100).toInt() - 50
    Robot(x, y).computePathsTo(x + m, y + n)
}

var plainComputations = 0
var computations = 0

class Robot(val x: Int, val y: Int) {
    fun computePathsTo(x: Int, y: Int) {
        println("Number of paths to ($x:$y) is ${compute(x, y)}")
    }

    private fun compute(m: Int, n: Int): Int {
        if (m < x || n < y) return 0
        if (x == m || y == n) return 1
        if (m - x == 1) return n - y + 1
        if (n - y == 1) return m - x + 1
        return recursiveComputation(m - x, n - y, Array(m - x) { IntArray(n - y) })
    }

    private fun recursiveComputation(m: Int, n: Int, cache: Array<IntArray>): Int {
        if (m == 0 || n == 0) return 1
        if (cache[m - 1][n - 1] == 0) {
            cache[m - 1][n - 1] = recursiveComputation(m - 1, n, cache) + recursiveComputation(m, n - 1, cache)
        }
        return cache[m - 1][n - 1]
    }
}
