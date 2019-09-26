package conditions

import enums.Color

fun main(args: Array<String>) {

    val n = 2
    val a = 5
    val b = 7

    // When, 'is'
    val w = when (n) {
        1 -> "one"
        2 -> "two"
        3 -> "three"
        else -> "none"
    }
    println(w)
    println(getColor(Color.GREEN))
    println(getOneOfTwoColors(Color.BLUE))
    moreOrLess(5, 6)
    isExample("String")

    // 'if' is expression, so you can use it instead ternary operator
    print("The least value between $a and $b is ")
    println(if (a<b) a else b)

}

fun getColor(color: Color) = when (color) {
    Color.RED -> "roses"
    Color.GREEN -> "grass"
    else -> "sea"
}

fun getOneOfTwoColors(color: Color) = when (color) {
    Color.RED, Color.BLUE -> "red or blue"
    Color.GREEN -> "green"
}

fun moreOrLess(a: Int, b: Int) = when {
    (a > b) -> println("$a>$b")
    (a < b) -> println("$a<$b")
    (a == b) -> println("$a=$b")
    else -> println("impossible")
}

fun isExample(s:Any) {
    when (s){
        is Int -> println("value is integer")
        is Boolean -> println("value is boolean")
        is String -> println("value is String")
        else -> println("value is unidentified")
    }
}
