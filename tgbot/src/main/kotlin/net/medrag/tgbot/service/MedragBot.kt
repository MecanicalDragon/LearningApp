package net.medrag.tgbot.service

import mu.KotlinLogging
import net.medrag.tgbot.callback.CallbackExecutor
import net.medrag.tgbot.command.AbstractCommand
import net.medrag.tgbot.config.MasterProps
import net.medrag.tgbot.util.BOT_NAME
import net.medrag.tgbot.util.callbackPrefix
import net.medrag.tgbot.util.username
import org.springframework.stereotype.Service
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import javax.annotation.PostConstruct

/**
 * @author Stanislav Tretyakov
 * 21.01.2021
 */
@Service
class MedragBot(
    commands: List<AbstractCommand>,
    executors: List<CallbackExecutor>,
    private val masterProps: MasterProps,
    private val masterMessageHandler: MasterMessageHandler,
    private val callbacks: MutableMap<String, CallbackExecutor> = HashMap()
) : TelegramLongPollingCommandBot() {

    init {
        for (command in commands) {
            register(command)
        }
        for (executor in executors) {
            callbacks[executor.getCallbackPrefix()] = executor
        }
    }

    @PostConstruct
    fun init() {
        TelegramBotsApi(DefaultBotSession::class.java).registerBot(this)
        logger.info("MedragBot has been registered.")
    }

    /**
     * Response on non-command
     * @param update Update
     */
    override fun processNonCommandUpdate(update: Update) {
        if (update.hasCallbackQuery()) {
            executeCallbackUpdate(update)
        } else {
            if (update.message?.from?.userName == masterProps.master) {
                masterMessageHandler.handleMasterRequest(update, this)
            } else {
                welcomeStranger(update)
            }
        }
    }

    private fun welcomeStranger(update: Update) {
        val message = update.message
        sendMessage(message.chatId, "Hello ${message.from.username()}!")
    }

    private fun executeCallbackUpdate(update: Update) {
        val callbackResult = callbacks[update.callbackPrefix()]?.executeCallback(update)
            ?: throw IllegalArgumentException()

        callbackResult.getExecutable()?.let {
            execute(it)
        }
    }

    /**
     * Return answer
     * @param chatId Long
     * @param text String
     */
    private fun sendMessage(chatId: Long, text: String) {
        SendMessage().apply {
            this.text = text
            this.chatId = chatId.toString()
        }.also {
            execute(it)
        }
    }

    override fun getBotToken(): String? {
        return masterProps.token
    }

    override fun getBotUsername(): String? {
        return BOT_NAME
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
