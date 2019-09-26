package objects

open class Parent(x: Int){
    open val y = x
    object Comp{
        val x = 5
        val y = 10
        fun create(): Parent = Parent(100)
    }
}