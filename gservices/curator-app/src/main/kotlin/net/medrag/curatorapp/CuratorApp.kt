package net.medrag.curatorapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CuratorApp

fun main(args: Array<String>) {
    runApplication<CuratorApp>(*args)
}
