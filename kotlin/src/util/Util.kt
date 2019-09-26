package util

fun importedMethod() {
    println("this string was imported from other package.")
}

//short entry
fun maxInt(a: Int, b: Int) = if (a > b) a else b

//function with default values
fun defaultValues(a: Int = 0, b: String = "") = b + a

//array positive filtration
fun positiveFilter(arr: Array<Int>) = arr.filter { it > 0 }

// instanceOf example
fun whatObjectIsThis(x: Any): String = when (x) {
    is Int -> "Integer"
    is String -> "String"
    else -> "Something else"
}

// static import example
fun isChar(char: Char, range:CharRange) = char in range
