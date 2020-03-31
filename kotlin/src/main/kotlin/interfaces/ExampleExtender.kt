package interfaces

class ExampleExtender(override var notDefined: String) : ExampleAbstract(), Exampleable {

    override fun printAbstract() {
        println("This method is excess here.")
    }

    override fun printDefault() {       // we must override this function, cause this class extends it from both of Exampleable and ExampleAbstract
        super<ExampleAbstract>.printDefault()       // how to execute 'printDefault' function of ExampleAbstract
        super<Exampleable>.printDefault()           // how to execute 'printDefault' function of Exampleable
    }
}