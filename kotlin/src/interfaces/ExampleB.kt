package interfaces

class ExampleB(override var notDefined: String) : Exampleable {
    override fun printAbstract() {
        println("overridden abstract method of 'Exampleable' in ExampleB implementation")
    }
}