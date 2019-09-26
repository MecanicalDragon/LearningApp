fun main(args: Array<String>) {


    val numbers: MutableList<Int> = mutableListOf(1, 6, 9)
    numbers[0] = 5
    numbers.add(8)
    numbers.forEach{print("$it--")}
    numbers.swap(1,2)
    println()
    numbers.forEach{print("$it--")}
    println()

    val strings: MutableList<String> = mutableListOf("asd", "zxc", "qwe")
    strings.forEach{print(it)}
    println()
    strings.swap(2,1)
    strings.forEach{print(it)}

}

fun <T> MutableList<T>.swap(x: Int, y: Int){
    val tmp = this[x]
    this[x] = this[y]
    this[y] = tmp
}