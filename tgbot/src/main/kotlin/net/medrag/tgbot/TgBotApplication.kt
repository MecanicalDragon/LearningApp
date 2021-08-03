package net.medrag.tgbot

import mu.KotlinLogging
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class TgBotApplication : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        logger.info("MedragBot is running!")
    }
}

fun main(args: Array<String>) {
    runApplication<TgBotApplication>(*args)
}

val logger = KotlinLogging.logger { }
