package classes

import functions.Example
import interfaces.Exampleable

fun main(){
    val anon = object : Exampleable{

        override var notDefined: String = "defined"

        override fun printAbstract() {
            println("print")
        }
    }

    anon.printAbstract()
    anon.printDefault()

    val anon2 = object : Example(){
        override fun defaultArg(a: String, b: String, c: String) {
            print(a)
            print(b)
            print(c)
        }
    }

    anon2.defaultArg("aaa", c = "ccc")
}