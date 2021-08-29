package net.medrag.tgbot.callback

import net.medrag.tgbot.model.zodiac.Zodiac
import net.medrag.tgbot.util.CALLBACK_PREFIX_ZODIAC
import net.medrag.tgbot.util.ZODIAC_MESSAGE_TEMPLATE
import net.medrag.tgbot.util.chatIdFromCallback
import net.medrag.tgbot.util.messageIdFromCallback
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.Update

/**
 * @author Stanislav Tretyakov
 * 07.02.2021
 */
@Component
class ZodiacCallbackExecutor : CallbackExecutor {

    override fun executeCallback(update: Update): CallbackExecutionResult {
        val zodiac = Zodiac.valueOf(extractCallbackPostfix(update))
        val text = String.format(
            ZODIAC_MESSAGE_TEMPLATE, zodiac, zodiac.ordinal + 1,
            zodiac.startDay, zodiac.startMonth, zodiac.endDay, zodiac.endMonth
        )

        val editMessage = EditMessageText.builder()
            .chatId(update.chatIdFromCallback())
            .messageId(update.messageIdFromCallback())
            .text(text).build()

        return BotApiMethodCallbackExecutionResult(editMessage)
    }

    override fun getCallbackPrefix() = CALLBACK_PREFIX_ZODIAC
}
