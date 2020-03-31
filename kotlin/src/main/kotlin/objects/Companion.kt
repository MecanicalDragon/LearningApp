package objects

class Companion{
    val i = "i am Companion"
    companion object Comp {
        fun create(): Companion = Companion()
    }
}