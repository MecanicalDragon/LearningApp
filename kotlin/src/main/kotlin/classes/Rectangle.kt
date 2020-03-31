package classes

// 'data'-keyword mean auto generating 'toString', 'equals', 'hashcode', 'clone', 'componentN'
data class Rectangle(var height:Int, var width: Int) {

//    private var height: Int = 0
//    private var width: Int = 0

    val isSquare: Boolean
        get() {
            return height == width
        }

    fun perimeter():Int{
        return width * height
    }
}