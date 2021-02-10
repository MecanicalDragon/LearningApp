package net.medrag.tgbot.callback

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import java.io.Serializable


/**
 * @author Stanislav Tretyakov
 * 10.02.2021
 */
class BotApiMethodCallbackExecutionResult(private val method: BotApiMethod<Serializable>?) : CallbackExecutionResult {
    override fun getExecutable() = method
}