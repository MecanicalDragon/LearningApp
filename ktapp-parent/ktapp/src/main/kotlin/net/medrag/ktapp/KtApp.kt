package net.medrag.ktapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @author Stanislav Tretiakov
 * 17.07.2023
 */
@SpringBootApplication
class KtApp

fun main(args: Array<String>) {
    runApplication<KtApp>(*args)
}