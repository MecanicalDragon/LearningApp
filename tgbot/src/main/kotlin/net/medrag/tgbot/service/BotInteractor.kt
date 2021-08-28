package net.medrag.tgbot.service

import net.medrag.tgbot.model.mediasaving.SaveMediaInfo

/**
 * @author Stanislav Tretyakov
 * 28.08.2021
 */
interface BotInteractor {
    fun downloadMedia(media: SaveMediaInfo)
}
