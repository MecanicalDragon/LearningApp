package interfaces

interface Exampleable {

    var notDefined:String
    val defined:String
    get() = "pre-defined String property"

    fun printAbstract()
    fun printDefault(){
        println("default method of interface Exampleable")
    }


}
