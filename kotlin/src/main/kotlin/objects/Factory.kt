package objects

interface Factory<T> {
    fun create(): T
}