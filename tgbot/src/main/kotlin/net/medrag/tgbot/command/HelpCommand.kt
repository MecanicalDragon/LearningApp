package net.medrag.tgbot.command

import jakarta.annotation.PostConstruct
import net.medrag.tgbot.util.idString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

/**
 * @author Stanislav Tretyakov
 * 08.02.2021
 */
@Component
class HelpCommand : AbstractCommand(
    "help",
    "returns list of commands"
) {

    @Autowired
    lateinit var botCommands: List<AbstractCommand>
    var message: String? = null

    override fun execute(p0: AbsSender?, p1: User?, p2: Chat?, p3: Array<out String>?) {

        SendMessage().apply {
            text = message ?: "NOT INITIALIZED YET"
            chatId = p2.idString()
        }.also {
            p0?.execute(it)
        }
    }

    @PostConstruct
    fun init() {
        val sb = StringBuilder("Available commands:")
        for (botCommand in botCommands) {
            sb.append("\nCommand: '${botCommand.commandIdentifier}' ${botCommand.description}")
        }
        message = sb.toString()
    }
}
