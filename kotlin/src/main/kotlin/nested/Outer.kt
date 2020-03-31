package nested

class Outer {
    private val bar: String = "Outer private val"

    //  like static in java
    class Nested {
        fun foo() = println("Outer Nested function")
    }

    // not static)
    inner class Inner {
        fun foo() = bar
    }
}