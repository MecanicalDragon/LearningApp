package objects

class CompanionWithFactory{
    val str = "String from Companion with factory"
    companion object : Factory<CompanionWithFactory> {
        override fun create(): CompanionWithFactory = CompanionWithFactory()
    }
}