fun main(args: Array<String>) {

    //labels are determined with @ symbol
    label@ for (a in 'a'..'z') {
        for (i in 1..100) {
            print("$a$i ")
            if (i == 10) {
                println("continue from label@")
                continue@label
            }
            if (a == 'f' && i == 5) {
                println("break label@")
                break@label
            }
        }
    }

    println()
    println()

    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)

    fooReturn(list)
    fooReturnForEach(list)
}

fun fooReturn(list: List<Int>) {
    list.forEach lit@{
        if (it == 5) {
            println("__break!")
            return@lit
        }
        print("$it::")
    }
}

fun fooReturnForEach(list: List<Int>) {
    list.forEach {
        if (it == 5) {
            println("__break again!")
            return@forEach
        }
        print("$it::")
    }
}