package net.medrag.tgbot

import net.medrag.tgbot.callback.CallbackExecutor
import net.medrag.tgbot.command.AbstractCommand
import net.medrag.tgbot.service.MedragBot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@SpringBootApplication
class TgBotApplication : ApplicationRunner {

    @Autowired
    lateinit var callbackExecutors: List<CallbackExecutor>

    @Autowired
    lateinit var botCommands: List<AbstractCommand>

    @Value("\${net.medrag.tg.bot.token}")
    lateinit var token: String

    override fun run(args: ApplicationArguments?) {

        val medragBot = MedragBot(token, botCommands, callbackExecutors)
        val botsApi = TelegramBotsApi(DefaultBotSession::class.java)

        botsApi.registerBot(medragBot)
        println("MedragBot is running!")
    }
}

fun main(args: Array<String>) {
    runApplication<TgBotApplication>(*args)
}
