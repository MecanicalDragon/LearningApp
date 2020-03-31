package interfaces

class ExampleA : Exampleable {
    override var notDefined: String
        get() = "still not defined String property"
        set(value) {}

    override fun printAbstract() {
        println("overridden abstract method of 'Exampleable' in ExampleA implementation")
    }

    override fun printDefault() {
        println("overridden default method of 'Exampleable' in ExampleA implementation")
    }
}