package enums

enum class Color(val hex :String, val opacity: Double? = 1.0) {

    RED("#111111"),
    GREEN("#222222", 0.5),
    BLUE("#333333");

    fun description() :String{
        return "This color is ${name}, it's HEX-string is ${hex}, opacity ${opacity}"
    }
}