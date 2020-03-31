package labels


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