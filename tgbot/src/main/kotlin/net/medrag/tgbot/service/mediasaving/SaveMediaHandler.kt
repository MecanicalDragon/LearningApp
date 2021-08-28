package net.medrag.tgbot.service.mediasaving

import net.medrag.tgbot.model.mediasaving.SaveMediaInfo
import org.telegram.telegrambots.meta.api.objects.Message

/**
 * @author Stanislav Tretyakov
 * 08.03.2021
 */
interface SaveMediaHandler {

    enum class MediaType {
        IMAGE,
        VIDEO,
        GIF
    }

    fun handleMedia(message: Message, path: String): SaveMediaInfo

    fun mediaType(): MediaType

    fun canBeUseful(message: Message): Boolean
}
