package sequences


/**
 * {@author} Stanislav Tretyakov
 * 27.01.2020
 */
class Man(val Name: String, val age: Int)

fun main() {
    val list = listOf(Man("Petr", 29), Man("Stas", 31), Man("Viktor", 15),
            Man("Arseniy", 68), Man("Michael", 47))
    val result = list.asSequence().fold(0){i, m -> i + m.age}
    println(result)
}