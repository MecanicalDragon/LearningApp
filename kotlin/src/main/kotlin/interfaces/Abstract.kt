package interfaces

abstract class Abstract {
    open var number = 10
    open fun abstractClassMethod() {
        println("This is abstract class method. Number = ${++number}")
    }
}