package net.medrag

import java.lang.ArithmeticException
import java.lang.Exception


/**
 * {@author} Stanislav Tretyakov
 * 09.01.2020
 */

// observable
// const & lateinit
// as

fun main() {

    labels()
    controlFlow()
    idioms()
    functions()

}

private fun functions() {

    // При вызове функций параметры, которым присвоены дефолтные значения
    // можно передавать выборочно
    overloaded(string = "Another string!", boolean = false)
    overloaded(int = 100500)
    overloaded()
    overloaded("any string value", boolean = false)


    val arr = arrayOf("string1", "string2", "string3")
    vararg(strings = *arr)


    // Локальные функции
    val charArray = local("Local")
    for (c in charArray) println(c)


    val multiplied = 5.multiply(5)
    println(multiplied)

    // Лямбды - тоже переменные определенного типа!
    // При единственном параметре часть (x) -> можно заменить на it
    val filterEven: (Int) -> Boolean = { it / 2 == 0 }
    val array = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
    val filtered = filterIntArray(array, filterEven)
    println(filtered)

}

private fun idioms() {

    // Collections access
    val list = ArrayList<String>()
    for (i in 1..10)
        list.add(i.toString())
    list[3] = "Fourth string"
    list[7] = "Eighth string"

    val map = HashMap<String, String>()
    map["key"] = "value"

    // Streams are deprecated!
    val filtered = list.filter { it.length > 5 }
    val upperCased = list.map { it.toUpperCase() }

    // String formatting
    val key = "key"
    println("The value ${map[key]} is mapped to the key $key")

    // Read-only collections
    var imList = listOf("a", "b", "c")
    val imMap = mapOf("a" to 1, "b" to 2, "c" to 3)

    // Null Safety
    val inc = map["other key"]?.length?.toBigInteger()?.bitCount()?.inc()
    println(inc)

    // If null then...
    val inc2 = map["other key"]?.length?.toBigInteger()?.bitCount()?.inc() ?: 0
    println(inc2)

    // If null then throw
    val inc3 = map["other key"]?.length?.toBigInteger()?.bitCount()?.inc()
            ?: throw Exception()
    println(inc3)

    // If you really want NPE
    val inc4 = map["other key"]!!.length
    println(inc4)

    // If not null then
    map["key"]?.let { println(it) }

}

private fun controlFlow() {

    val range = 1..10
    val i = 5

    // Switch is deprecated too
    when (i) {
        !in range -> println("i is not in range")
        1, 3, 5, 7, 9 -> println("i is odd")
        2, 4 -> {
            println("i is less than 5")
        }
    }

    // if when is used as expression, 'else' is mandatory,
    // if compiler can not be sure, that all cases are covered
    val result: String = when (i) {
        in range -> "In range"
        13 -> "lucky number"
        else -> "exceeds range"
    }
    println(result)

    // instead of ternary
    val stringNumber = if (Math.random() > 0.5)
        "Really great number" else "Very small number"
    println(stringNumber)

    // even the familiar try-catch!
    val catched = try {
        Math.random()
    } catch (e: ArithmeticException) {
        0.0
    }
    println(catched)

}

private fun labels() {

    //labels
    label@ for (i in 1..100) {
        for (j in 1..100) {
            if (i == j) {
                println("i == j == $i! Amazing!")
                continue@label
            }
        }
    }

    fun foo() {
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return
            // нелокальный возврат к вызову функции foo()
            print(it)
        }
        println("эта строка недостижима")
    }

    fun foo2() {
        listOf(1, 2, 3, 4, 5).forEach lit@{
            if (it == 3) return@lit
            // локальный возврат внутри лямбды, то есть к циклу forEach
            print(it)
        }
        println("выполнится с использованием явной метки(lit@)")
    }

    fun foo3() {
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@forEach
            // локальный возврат внутри лямбды, то есть к циклу forEach
            print(it)
        }
        println("выполнится с использованием неявной метки(forEach@)")
    }

}


// part 1


fun print(s: String) {
    println("Печатаем $s")
}

fun printAgain(s: String): Unit = println("Печатаем $s")

fun printAndReturn(s: String): String {
    println("Длина строки $s равна ${s.length} символам")
    return s;
}

fun calculate(x: Int, y: Int): Int {
    return x + y
}

fun calculateAgain(x: Int, y: Int) = x + y


// part 2


// Просто пустой и бесполезный класс
class EmptyClass

// Класс с конструктором
class ClassWithConstructor private constructor(name: String)

// Ключевое слово 'constructor' можно опустить, если к нему
// не применяются аннотации и модификаторы видимости
class ClassWithDefaultConstructor(name: String)

// Класс c небесполезным конструктором
class ClassWithInitMethod2(name: String) {
    val name: String = name
}

// Класс с инициализирующим методом
class ClassWithInitMethod(name: String) {

    private val name: String

    init {
        printName(name)
        this.name = name
    }

    fun printName(name: String) = println("The name is $name")
}

// Класс с наиболее короткой записью
class SimplifiedClass(val name: String)


// part 3


// Класс с более, чем одним конструктором
class ClassWithSecondaryConstructor(name: String) {

    var name = name
    var surname: String = "Defaultovich"
    var age: Int = 0

    constructor(name: String, lastName: String) : this(name) {
        this.surname = lastName
    }
}

// В конструкторах мы можем указывать параметры по умолчанию
class ClassWithDefaultParams(
        val name: String = "Default",
        val surname: String = "Defaultovich",
        val age: Int = 0
)

// Классы данных автоматически генерируют методы
// equals(), hashCode(), toString(), copy(), componentN()
// Дата-классы не могут наследоваться от других классов,
// но могут реализовывать интерфейсы.
data class DataClass(
        val name: String,
        val surname: String,
        val age: Int
)


// part 4


// Классы в Котлине по умолчанию final, так что, если мы хотим
// наследоваться от них, нам нужен модификатор open
open class OpenClass(
        open val name: String,
        val surname: String,
        val age: Int = 0) {
    constructor(name: String) : this(name, "Defaultovich")
}

interface Extendable {
    fun go()
    fun defaultGo() = println("default go function")
}

class Subclass(
        override val name: String = "OverridDen"
) : OpenClass(name), Extendable {
    override fun go() = println("overridden go function")
}


// part 5


// Для полей класса по умолчанию добавляются геттеры и сеттеры,
// но мы при необходимости можем их и переопределить
class WithFields {
    var name = "Ivan"
        get() = field.toUpperCase()
        set(value) {
            field = value.toLowerCase()
            println("Name is $field")
        }
}

// Ленивая инициализация свойств
class LazyField {
    val name: String by lazy {
        println("Computing lazy name...")
        "Lazy"
    }
}

// Статические данные классов
class WithStaticDataClass {
    companion object {
        const val NAME = "STATIC_NAME"
    }
}


// part 6


fun overloaded(string: String = "some string",
               int: Int = 99, boolean: Boolean = true) {
    println("Overloaded function is invoked!")
    println("String value is $string")
    println("Integer value is $int")
    println("Boolean value is $boolean")
}

fun vararg(vararg strings: String) {
    for (s in strings) println(s)
}

fun local(s: String): CharArray {
    fun inner(s: String): CharArray = s.toCharArray()
    return inner(s)
}

// Функции-расширения
fun Int.multiply(x: Int) = this * x

fun filterIntArray(array: Array<Int>,
                   lambda: (Int) -> Boolean): ArrayList<Int> {
    val list = ArrayList<Int>()
    for (it in array)
        if (lambda(it))
            list.add(it)
    return list
}


