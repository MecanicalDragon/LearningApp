import classes.DataClass
import classes.Rectangle
import generics.Box
import interfaces.*
import labels.fooReturn
import labels.fooReturnForEach
import properties.CONST
import properties.PropExample
import util.importedMethod

const val separator = "___________________"

fun main(args: Array<String>) {

//    putNothing<Nothing>()
//    unsortedBasics()
//    extensions()
//    labelsExample()
//    inline()
//    properties()
    elvis()
}

fun elvis() {
    val x: String? = getNullable()
    val y: String = x?.let {
        println("one")
        "one"
    } ?: orElse()
    println(y)
}

fun getNullable(): String? = "null"

fun orElse(): String {
    println("two")
    return "two"
}

fun <T> putNothing(nothing: T? = null) {
    println(nothing)
}

fun properties() {
    val props = PropExample()

    println(separator)
    println("Custom getter work instantiation:")
    println(props.customGetterVal)
    println(props.customGetterVal)
    println(props.customGetterVal)

    println(separator)
    println("Custom setter work instantiation:")
    println(props.customSetterVar)
    println(props.customSetterVar)
    println("Setting value to 10...")
    props.customSetterVar = 10
    println(props.customSetterVar)
    println(props.customSetterVar)

    println(separator)
    println("Lazy initialization instantiation:")
    println(props.lazyField)
    println(props.lazyField)
    println(props.lazyField)

    println(separator)
    println("Lateinit field access instantiation:")
    try {
        println(props.lateInitField)
    } catch (e: UninitializedPropertyAccessException) {
        println("Access to not initialized lateinit field produces '${e.message}' exception")
    }
    props.lateInitField = CONST
    println(props.lateInitField)
}

fun labelsExample() {

    // labels are determined by '@' symbol
    label@ for (a in 'a'..'z') {
        for (i in 1..100) {
            print("$a$i ")
            if (i == 10) {
                println("continue from label@")
                continue@label
            }
            if (a == 'f' && i == 5) {
                println("break label@")
                break@label
            }
        }
    }

    println()
    println(separator)

    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)

    fooReturn(list)
    fooReturnForEach(list)
}

private fun unsortedBasics() {

    fun returnOne(): Int {
        return 1
    }

    fun intToLong(x: Int): Long {
        return x.toLong()
    }

    fun addition(a: Int, b: Int) = a + b

    fun getNull(): String? { // '?' allows to return null
        return null
    }

    println(separator)
    println("Hello Kotlin")
    println(separator)

    // var - variable can be reassigned
    // val - variable can NOT be reassigned, consider it as FINAL variable
    // const val - constant, allowed only top-level or object-related Strings and primitives
    // lateinit var - used to avoid null checks, when variable is null during compilation, but initialized on startup.

    val s: String = "String"
    println("This is $s - yes, it is")
    println("String's above length is ${s.length}")
    println("This is \$s - USD")
    println(separator)

    var a: Int = 1
    var b: Int = 2
    println("A+B=${a + b}")
    println(separator)

    var r = Rectangle(5, 4)
    println("classes.Rectangle perimeter = ${r.perimeter()}")
    println(separator)

    // Methods
    print(returnOne())
    print(":::")
    print(intToLong(s.length))
    print(":::")
    println(addition(a, b))
    importedMethod()
    println(separator)

    // Interfaces
    val eA: Exampleable = ExampleA()
    val eB: Exampleable = ExampleB("post-defined String property")
    eA.printAbstract()
    eA.printDefault()
    eB.printAbstract()
    eB.printDefault()
    println(eA.defined)
    println(eA.notDefined)
    println(eB.defined)
    println(eB.notDefined)
    println(separator)

    // Abstract
    // methods and fields are final by default. To allow overriding, they must be declared as 'open'
    var abs = AbstractExtender(5)
    abs.abstractClassMethod()
    abs = AbstractExtenderExtender(15)
    abs.abstractClassMethod()
    println(separator)

    /*
    Modifiers:
    public - set by default. Available from everywhere.
    internal - everywhere within module
    protected - within the class and from all descendants
    private - var available only within {}
    By the way, outer class can't see private variables of his inner class.
     */

    // POJO
    // 'data'-keyword mean auto generating 'toString', 'equals', 'hashcode', 'clone'
    var r1 = Rectangle(10, 20)
    var r2 = r1.copy(width = 30)
    val (hei, wid) = r1
    println(r1)
    println(r2)
    println("classes.Rectangle width is $wid and height is $hei")
    println(separator)

    // Collections (Read-Only), 'it'
    var list = listOf<String>("Name", "Surname", "Third name")
    list.forEach { print("$it - ") }
    println("")
    var set = setOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
    set.forEach { print("$it:::") }
    println("")
    var map = mapOf<String, String>("Name" to "Stanislav", "Surname" to "Tretyakov", "Third name" to "Alexeevich")
    map.forEach { println(it) }
    for ((k, v) in map) {
        println("$k -> $v")
    }
    println(separator)

    // Data class
    val dc = DataClass("Just Data", 35)
    println(dc)
    println(dc.component1())
    println(dc.component2())

    val dc2 = dc.copy(amount = 20)
    println(dc2)
    println(dc2.component1())
    println(dc2.component2())
    println(separator)

    // Generics
    val box = Box(1)
    println(box.t)

    // NullPointer
    Thread.sleep(100)
    val sNull: String? //  '?' allows var to be null
    sNull = getNull()
    val sNullLength = sNull!!.length //  '!!' allows NPE
}
