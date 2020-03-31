package interfaces

class AbstractExtenderExtender(var numb :Int) : AbstractExtender(numb) {
    override fun abstractClassMethod() {
        println("This is abstract class method, overriden in it's descendant. Number = ${--number}")
    }
}