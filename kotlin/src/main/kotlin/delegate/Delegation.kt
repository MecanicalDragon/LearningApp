package delegate

import kotlin.jvm.javaClass
import kotlin.properties.Delegates.observable
import kotlin.reflect.KProperty

interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() { println(x) }
}

class Derived(b: Base) : Base by b

class Example {
    var str: String by Delegate()
    val lazyValue: String by lazy {
        println("Now lazyValue is required for the 1st time, and it just had been initialized.")
        "This function is synchronized by default. Use 'LazyThreadSafetyMode.NONE'-mode, if you're sure in single thread."
    }
    var observableValue: String by observable("undefined") {
        prop, old, new ->
        println("observableValue has been renamed from $old new $new. Here is it's property: '$prop'")
    }
    override fun toString(): String = "object with class name ${this.javaClass}"
}

// in fact, this class overrides getter and setter of field, that implement it
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String = "$thisRef, ������� �� ������������� ��� '${property.name}'!"
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) = println("$value ���� ��������� �������� '${property.name} � $thisRef.'")
}

class Man(map: Map<String, Any?>) {
    val name: String by map
    val age: Int     by map
    override fun toString(): String = "$name, $age years old."
}

fun main(args: Array<String>) {

    val b = BaseImpl(10)
    Derived(b).print() // prints 10

    val e = Example();
    e.str = "string set"
    println(e.str)

    for (value in 1..3){
        println(e.lazyValue)
    }

    e.observableValue = "first"
    e.observableValue = "second"
    //���� ��� ����� ����� ����������� ��������� ������������ ��������� ��������, ����������� ������� vetoable() ������ observable().

    val map = mutableMapOf<String, Any>()
    map.put("name", "Vasiliy")
    map.put("age", 30)

    val man = Man(map)
    println(man)

}

//������ ������������� �������� ������� ������������� ������������, � Kotlin ������������ ��� �������,
// ���������� ��� �� ������������� ��������� ���������� ����.
//�������� ����� by � ���������� Derived, ����������� ����� ���� ������������� ������, ������� � ���, ���
// ������ b ���� Base ����� ��������� ������ ���������� Derived, � ���������� ����������� � Derived
// ��������������� ������ �� Base, ������� ��� ������ ����� �������� ������� b

//local lazy variable
//fun example(computeFoo: () -> Foo) {
//    val memoizedFoo by lazy(computeFoo)
//
//    if (someCondition && memoizedFoo.isValid()) {
//        memoizedFoo.doSomething()
//    }
//}