package nested

fun main(args: Array<String>) {

    Outer.Nested().foo()

    println(Outer().Inner().foo())

    // anonymous class:
    /**
    window.addMouseListener(object: MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            // ...
        }

        override fun mouseEntered(e: MouseEvent) {
            // ...
        }
    })
    */

}