package enums

fun main(args: Array<String>) {

    val blue: Color = Color.BLUE
    println(blue)
    println(blue.name)
    println(blue.opacity)
    println(blue.ordinal)
    println(blue.declaringClass)
    println(blue.hex)
    println(Color.RED.description())
    println()

    val wait = TrafficLight.WAITING
    wait.signal()
    val go: TrafficLight = TrafficLight.WALKING
    go.signal()

}