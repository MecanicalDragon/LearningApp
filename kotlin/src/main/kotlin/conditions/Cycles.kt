package conditions

import util.isChar

fun main(args: Array<String>) {

    // Cycles, 'in'
    val s = "String"

    val nums = 1..10
    for (value in nums){
        print("$value::")
    }
    println("")
    for (value in nums step 2){
        print("$value::")
    }
    println("")
    for (value in s.length downTo 1 step 2){
        print("$value::")
    }
    println("")
    for ((index, value) in s.withIndex()) {
        println("Char at $index is $value")
    }
    println("")
    val chars = 'a'..'z'
    println(isChar('#', chars))

}

