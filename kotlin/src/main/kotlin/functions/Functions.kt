package functions

fun main(args: Array<String>) {

    Example().defaultArg()
    Example2().defaultArg("Overridden functions", " use the same default values, that used in parent")
    defaultArgsFirst(a = "You can mix default", b = "and specified arguments in a ")
    overloaded("Count with me:", d = " Fuck You!")
    spread(1,2,3,5)

    val arr = IntArray(3)
    arr[0] = 10
    arr[1] = 100
    arr[2] = 22220
    spread(999, *arr, 555)

    outer("Test String")
    parametrized("parametrized")
}

open class Example {
    open fun defaultArg(a: String = "default", b: String = " argument ", c: String = "function") = println(a + b + c)
}

class Example2 : Example() {
    override fun defaultArg(a: String, b: String, c: String) = println(a + b + c)
}

fun defaultArgsFirst(a: String = "default ", b: String = "arguments", c: String = "function") = println(a + b + c)

fun overloaded(a: String, b: String = " one", c: String = " two", d: String = " three") = println(a+b+c+d)

fun spread(vararg x: Int) {
    for (y in x) println(y)
}

fun outer(string: String){
    val s = string.length
    fun inner(){
        println("Result if inner function is $s")
    }
    inner()
}

fun <T> parametrized(value: T){
    println(value)
}

tailrec fun findFixPoint(x: Double = 1.0): Double = if (x == Math.cos(x)) x else findFixPoint(Math.cos(x))
//code above is equal code below:

//private fun findFixPoint(): Double {
//    var x = 1.0
//    while (true) {
//        val y = Math.cos(x)
//        if (x == y) return y
//        x = y
//    }
//}

// Для соответствия требованиям модификатора tailrec, функция должна вызывать сама себя в качестве последней операции,
// которую она предпринимает. Вы не можете использовать хвостовую рекурсию, когда существует ещё какой-то код после
// вызова этой самой рекурсии. Также нельзя использовать её внутри блоков try/catch/finally. На данный момент,
// хвостовая рекурсия поддерживается только в backend виртуальной машины Java(JVM).