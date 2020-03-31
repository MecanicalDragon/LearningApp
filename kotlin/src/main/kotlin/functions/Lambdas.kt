package functions

class Lambder {
    companion object {
        fun intOperation(): Int {
            val x = 5
            val y = 7
            val z = 15
            val s = x + y + z
            return s
        }

        fun stringOperation(): String {
            val x = "string"
            val y = "demonstration"
            val z = "lambda"
            val w = " "
            val s = z + w + x + w + y
            return s
        }
    }
}

fun main(args: Array<String>) {

    var intArr = mutableListOf(1, 2, 3)
    intArr = lambda(intArr, ::intBody)

    var stringArr = mutableListOf("one", "two", "three")
    stringArr = lambda(stringArr, ::stringBody)

    intArr = lambda(intArr, { Lambder.intOperation() })
    stringArr = lambda(stringArr) { Lambder.stringOperation() }
    // There is a convention in Kotlin, if last argument of the function is lambda, you can convey it out of braces

    println(intArr)
    println(stringArr)

    val stringLengthIntArray = stringArr.extensionFunction(::transform)
    println(stringLengthIntArray)

    val otherStringLengthIntArray = stringArr.extensionFunction { it.hashCode() }
    // If lambda is the only argument, you can specify no braces at all
    // If lambda has the only parameter, it can be denoted as 'it'
    println(otherStringLengthIntArray)

    // unused parameters can be replaced with '_'
    HashMap<String, String>().forEach { _, value -> println("$value!") }

}

fun <T> lambda(array: MutableList<T>, body: () -> T): MutableList<T> {
    val x = body()
    array.add(x)
    return array
}

fun stringBody() = "body"
fun intBody() = 555

fun transform(s: String): Int{
    return s.length + 5
}

fun <S, I> MutableList<S>.extensionFunction(lambda: (S) -> I): List<I> where S: String, I: Int {
    val result = ArrayList<I>()
    for (item in this){
        result.add(lambda(item))
    }
    return result
}