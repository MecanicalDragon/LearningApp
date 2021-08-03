package net.medrag.tgbot.callback

import mu.KotlinLogging
import net.medrag.tgbot.util.CALLBACK_PREFIX_DEMO
import net.medrag.tgbot.util.chatIdFromCallback
import net.medrag.tgbot.util.userFromCallback
import net.medrag.tgbot.util.username
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.Serializable

/**
 * @author Stanislav Tretyakov
 * 08.02.2021
 */
@Component
class DemoCallbackExecutor : CallbackExecutor {
    override fun executeCallback(update: Update): CallbackExecutionResult {

        val username = update.userFromCallback().username()
        val answer = "$username сходил на хуй."
        logger.info(answer)

        val message: SendMessage = SendMessage.builder()
            .text(answer)
            .chatId(update.chatIdFromCallback())
            .build()

        @Suppress("UNCHECKED_CAST")
        return BotApiMethodCallbackExecutionResult(message as? BotApiMethod<Serializable>)
    }

    override fun getCallbackPrefix(): String = CALLBACK_PREFIX_DEMO

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
