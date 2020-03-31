package enums

// additional anonymous class example
enum class TrafficLight{

    WAITING {
        override fun signal() {
            println("YOU MUST WAIT!!!")
        }
    },
    WALKING {
        override fun signal() {
            println("YOU CAN WALK.")
        }
    };

    abstract fun signal()
}