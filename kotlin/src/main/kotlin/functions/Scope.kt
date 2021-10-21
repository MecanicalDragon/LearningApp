package functions

/**
Функция	Обращение к объекту	Возвращаемое значение	Является функцией-расширением
let	    it	                Результат лямбды	    Да
run	    this	            Результат лямбды	    Да
run	    -	                Результат лямбды	    Нет: может быть вызвана без контекстного объекта
with	this	            Результат лямбды	    Нет: принимает контекстный объект в качестве аргумента.
apply	this	            Контекстный объект	    Да
also	it	                Контекстный объект	    Да

Краткое руководство по выбору функции области видимости в зависимости от предполагаемого назначения:

Выполнение лямбды для non-null объектов: let
Представление переменной в виде выражения со своей локальной областью видимости: let
Настройка объекта: apply
Настройка объекта и вычисление результата: run
Выполнение операций, для которых требуется выражение: run без расширения
Применение дополнительных значений: also
Группировка всех функций, вызываемых для объекта: with
 *
 * @author Stanislav Tretyakov
 * 09.02.2021
 */
class Scope(var name: String, var age: Int) {
    constructor() : this("Vasiliy", 32)
}

fun main(args: Array<String>) {
//    letUsage()
//    withUsage()
//    runUsage()
//    applyUsage()
//    alsoUsage()
    takeUsage()
}

/**
 * takeIf - returns object if it matches predicate, otherwise null
 * takeUnless - returns object if it doesn't match predicate, otherwise null
 *
 * Usage:
 *  - val.takeIf { predicate }?.let { usage }
 */
fun takeUsage() {
    val x = 5

    val y = x.takeIf { it % 2 != 0 }
    println(y)

    val z = x.takeUnless { it % 2 == 0 }
    println(z)

    val list = listOf("not empty")
    val result = list.takeUnless { it.isEmpty() } ?: listOf("default")
    println(result)

    val list2 = emptyList<String>()
    val result2 = list2.takeIf { it.isNotEmpty() } ?: listOf("default")
    println(result2)
}

/**
 * context object - it
 * returns - context object
 *
 * Usage:
 *  - действия, которые принимают контекстный объект в качестве аргумента
 *  - требуется ссылка именно на объект, а не на его свойства и функции
 */
fun alsoUsage() {
    Scope().also {
        println(it)
    }
}

/**
 * context object - this
 * returns - context object
 *
 * Usage:
 *  - настройка объекта-получателя
 */
fun applyUsage() {
    val result = Scope().apply {
        name = "Alex"
        age = 26
    }
    println(result)
}

/**
 * context object - this
 * returns - lambda result
 *
 * Usage:
 *  - делает то же самое, что и with, но вызывается как let
 *  - удобен, когда лямбда содержит и инициализацию объекта, и вычисление возвращаемого значения
 */
fun runUsage() {
    val result = Scope().run {
        println(name)
        println(age)
        "Scope is $name-$age"
    }
    println(result)
}

/**
 * Not an extension.
 * context object - this
 * returns - lambda result
 *
 * Usage:
 *  - вызов функций контекстного объекта без предоставления результата лямбды
 *  - введение вспомогательного объекта, свойства или функции которые будут использоваться для вычисления значения
 */
fun withUsage() {
    val scope = Scope()
    with(scope) {
        println(this)
    }
    val result = with(scope) {
        "Scope is $name-$age"
    }
    println(result)
}

/**
 * context object - it
 * returns - lambda result
 *
 * Usage:
 *  - позволяет использовать полученный результат цепочки операций для вызова одной или нескольких функций в блоке кода
 *  - для выполнения блока кода только с non-null значениями
 *  - введение локальных переменных с ограниченной областью видимости для улучшения читабельности кода
 */
fun letUsage() {
    val result = Scope().let {
        println(it.name)
        println(it.age)
        "Scope is ${it.name}-${it.age}"
    }
    println(result)
}
