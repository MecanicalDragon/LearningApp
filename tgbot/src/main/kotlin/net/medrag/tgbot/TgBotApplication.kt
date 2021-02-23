package net.medrag.tgbot

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class TgBotApplication : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        println("MedragBot is running!")
    }
}

fun main(args: Array<String>) {
    runApplication<TgBotApplication>(*args)
}
