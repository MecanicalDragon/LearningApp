package net.medrag.tgbot.service

import net.medrag.tgbot.model.mediasaving.SaveMediaInfo
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

/**
 * @author Stanislav Tretyakov
 * 28.08.2021
 */
interface BotInteractor {
    fun downloadMedia(media: SaveMediaInfo)
    fun respond(update: Update, message: String)
    fun getSender(): AbsSender
}
