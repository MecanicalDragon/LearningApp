package functions


/**
 * @author Stanislav Tretyakov
 * 09.02.2021
 */
fun main(args: Array<String>) {
    var s = "Hello, operator!"
    println(--s)
    println(--s)
    println(--s)
    println(--s)
}

operator fun String.dec(): String = this.substring(0, this.length - 1)
