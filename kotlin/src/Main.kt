import classes.DataClass
import generics.Box
import interfaces.*
import util.importedMethod

   const val separator = "___________________"

    //Расширения
    //Изолированные классы
    //Обобщения

fun main(args: Array<String>) {

    println(separator)
    println("Hello Kotlin")
    println(separator)

    var s: String = "String"
    println("This is $s - yes, it is")
    println("String's above length is ${s.length}")
    println("This is \$s - USD")
    println(separator)

    var a: Int = 1
    var b: Int = 2
    println("A+B=${a + b}")
    println(separator)

    // Objects, variables and values
    val user = User("John Wayne", 30)
    // var - mutable
    // val - immutable
    // as an object field var equals common variable, val - final variable
    // const val - constant - something like static variable.
    //lateinit var - used to avoid null checks, when variable is null while compilation, but initialized on startup.
    println(DESCRIPTION)
    println("First user's name is ${user.name}")
    println(user.name + " is bold: " + user.bold)
    println(user.name + " stinks: " + user.stinky)
    user.stinky = false
    println("Now ${user.name} stinks: ${user.stinky}")

    var r = Rectangle(5, 4);
    println("Rectangle perimeter = ${r.perimeter()}")
    println(separator)

    // Methods
    print(returnOne())
    print(":::")
    print(intToLong(s.length))
    print(":::")
    println(adiition(a, b))
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
    var r1 = Rectangle(10,20)
    var r2 = r1.copy(width = 30)
    val(hei, wid) = r1
    println(r1)
    println(r2)
    println("Rectangle width is ${wid} and height is ${hei}")
    println(separator)

    // Collections (Read-Only), 'it'
    var list = listOf<String>("Name", "Surname", "Third name")
    list.forEach { print("$it - ") }
    println("")
    var set = setOf<Int>(1,2,3,4,5,6,7,8,9,0)
    set.forEach { print("$it:::") }
    println("")
    var map = mapOf<String, String>("Name" to "Stanislav", "Surname" to "Tretyakov", "Third name" to "Alexeevich")
    map.forEach { println(it) }
    for ((k,v) in map){
        println("$k -> $v")
    }
    println(separator)


    // Data class
    val dc = DataClass("Just Data", 35);
    println(dc)
    println(dc.component1())
    println(dc.component2())

    val dc2 = dc.copy(amount = 20)
    println(dc2)
    println(dc2.component1())
    println(dc2.component2())
    println(separator)

    //Generics
    val box = Box(1)
    println(box.t)

    // NullPointer
    Thread.sleep(100)
    val s_null: String? //  '?' allows var to be null
    s_null = getNull()
    val null_length = s_null!!.length   //  '!!' allows NPE


}

fun returnOne(): Int {
    return 1
}

fun intToLong(x: Int): Long {
    return x.toLong()
}

fun adiition(a: Int, b: Int) = a + b

fun getNull(): String? { // '?' allows to return null
    return null
}