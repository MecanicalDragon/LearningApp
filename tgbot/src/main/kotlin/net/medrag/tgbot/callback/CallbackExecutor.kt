package net.medrag.tgbot.callback

import net.medrag.tgbot.util.CALLBACK_DELIMITER
import org.telegram.telegrambots.meta.api.objects.Update

/**
 * @author Stanislav Tretyakov
 * 07.02.2021
 */
interface CallbackExecutor {
    fun executeCallback(update: Update): CallbackExecutionResult

    fun getCallbackPrefix(): String

    fun extractCallbackPostfix(update: Update) = update.callbackQuery.data.split(CALLBACK_DELIMITER)[1]
}
