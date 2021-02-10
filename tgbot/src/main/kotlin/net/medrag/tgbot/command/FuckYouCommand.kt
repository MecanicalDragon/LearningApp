package net.medrag.tgbot.command

import net.medrag.tgbot.util.CALLBACK_DELIMITER
import net.medrag.tgbot.util.CALLBACK_PREFIX_DEMO
import net.medrag.tgbot.util.idString
import net.medrag.tgbot.util.username
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender


/**
 * @author Stanislav Tretyakov
 * 08.02.2021
 */
@Component
class FuckYouCommand : AbstractCommand(
        "whatyoucan",
        "рассказывает о возможностях бота"
) {
    override fun execute(sender: AbsSender?, user: User?, chat: Chat?, p3: Array<out String>?) {

        SendMessage()
                .apply {
                    this.text = "Нахуй тебя посылать.\nПошел нахуй, ${user.username()}!"
                    this.chatId = chat.idString()
                    this.replyMarkup = buildReplyMarkup()
                }.also {
                    sender?.execute(it)
                }
    }

    private fun buildReplyMarkup(): InlineKeyboardMarkup {
        val row = emptyList<InlineKeyboardButton>().toMutableList()
        for (answer in listOf("Понял.", "Принял.", "Пошёл.")) {
            row.add(buildButton(answer))
        }
        return InlineKeyboardMarkup.builder().keyboardRow(row).build()
    }

    private fun buildButton(answer: String) = InlineKeyboardButton.builder()
            .text(answer)
            .callbackData(CALLBACK_PREFIX_DEMO + CALLBACK_DELIMITER + answer)
            .build()
}