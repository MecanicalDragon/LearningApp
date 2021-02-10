package functions

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

fun main(args: Array<String>) {
    val atomic = AtomicBoolean(false)
    for (i in 1..10){
        GlobalScope.launch {
            if (!atomic.lockForAction {
                        println("Coroutine $i acquires atomic")
                        println("Atomic is locked by Coroutine $i: ${atomic.get()}")
                        delay(1000)
                        println("Coroutine $i releases atomic")
                    }){
                println("Coroutine $i could not acquire atomic :(")
            }
            println("Atomic state after Coroutine $i execution is ${atomic.get()}")
        }
    }
    Thread.sleep(5000)
}

/**
 * Functions acquires the lock on boolean before invocation and releases after.
 * Suppose this is not correct example of inline function.
 *
 * Thorough description here:
 * https://www.baeldung.com/kotlin-inline-functions
 *
 * @receiver AtomicBoolean
 * @param block Function0<Unit>
 * @return Boolean - was the lock acquired or not
 */
inline fun AtomicBoolean.lockForAction(block: () -> Unit): Boolean {
    if (this.compareAndSet(false, true)) {
        try {
            block.invoke()
        } finally {
            this.set(false)
            return true
        }
    } else return false
}