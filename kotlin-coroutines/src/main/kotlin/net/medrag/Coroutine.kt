package net.medrag

import kotlinx.coroutines.*
import java.lang.Thread.sleep
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


/**
 * {@author} Stanislav Tretyakov
 * 06.12.2019
 */
val dtf = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
fun main(){
    println("Start")

//    suspendingInstantiate()
//    asyncFun()
    lazyAsyncFun()

    Thread.sleep(10000) // wait for 10 seconds
    println("Stop")
}

/**
 * Instantiate async functions
 */
fun lazyAsyncFun() {
    GlobalScope.launch {
        println("Hello lazy async function: ${LocalTime.now().format(dtf)}")
        val x = async(start = CoroutineStart.LAZY) { async1() }
        val y = async(start = CoroutineStart.LAZY) { async2() }
        println("waiting 3 sec...")
        delay(3000)
        println("now let's calculate async result")
        x.start()
        y.start()
//        if we do not start async functions explicitly and try to calculate result, it will be invoked sequentially
        println("waiting for async results...")
        println("result is ${x.await() + y.await()} - ${LocalTime.now().format(dtf)}")
    }
}

/**
 * Instantiate async functions
 */
fun asyncFun() {
    GlobalScope.launch {
        println("Hello async function: ${LocalTime.now().format(dtf)}")
        val x = async { async1() }
        val y = async { async2() }
        println("waiting for async results")
        println("result is ${x.await() + y.await()} - ${LocalTime.now().format(dtf)}")
    }
}

fun async1(): Int {
    println("started 3sec async1: ${LocalTime.now().format(dtf)}")
    sleep(3000)
    println("async1 finished: ${LocalTime.now().format(dtf)}")
    return 40
}

fun async2(): Int {
    println("started 3sec async2: ${LocalTime.now().format(dtf)}")
    sleep(3000)
    println("async2 finished: ${LocalTime.now().format(dtf)}")
    return 2
}

/**
 * instantiate suspending function
 */
fun suspendingInstantiate() {
    GlobalScope.launch {
        println("Hello suspending instantiate")
        pause()
        println("After resume")
    }
}

/**
 * this function suspends coroutine
 */
suspend fun pause() {
    println("started suspend function")
    println("take 3sec pause: ${LocalTime.now().format(dtf)}")
    delay(3000)
    println("resume: ${LocalTime.now().format(dtf)}")
}
