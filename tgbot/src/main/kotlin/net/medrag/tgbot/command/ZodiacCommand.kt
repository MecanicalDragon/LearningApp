package net.medrag.tgbot.command

import net.medrag.tgbot.model.zodiac.Zodiac
import net.medrag.tgbot.util.CALLBACK_DELIMITER
import net.medrag.tgbot.util.CALLBACK_PREFIX_ZODIAC
import net.medrag.tgbot.util.idString
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender

/**
 * @author Stanislav Tretyakov
 * 21.01.2021
 */
@Component
class ZodiacCommand : AbstractCommand(
    "zodiac",
    "invokes zodiac picker"
) {
    override fun execute(absSender: AbsSender?, user: User?, chat: Chat?, params: Array<out String>?) {

        val ikm = InlineKeyboardMarkup.builder()
        var row = emptyList<InlineKeyboardButton>().toMutableList()

        for (value in Zodiac.values()) {
            row.add(buildZodiacButton(value))
            if (row.size == 4) {
                ikm.keyboardRow(row)
                row = emptyList<InlineKeyboardButton>().toMutableList()
            }
        }

        SendMessage().apply {
            text = "Pick your Zodiac"
            chatId = chat.idString()
            replyMarkup = ikm.build()
        }.also {
            absSender?.execute(it)
        }
    }

    private fun buildZodiacButton(zodiac: Zodiac) = InlineKeyboardButton.builder()
        .text(zodiac.emoji)
        .callbackData(CALLBACK_PREFIX_ZODIAC + CALLBACK_DELIMITER + zodiac.toString())
        .build()
}
