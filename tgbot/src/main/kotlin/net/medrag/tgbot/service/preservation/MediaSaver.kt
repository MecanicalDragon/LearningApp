package net.medrag.tgbot.service.preservation

import net.medrag.tgbot.model.preservation.SavedMediaInfo
import org.telegram.telegrambots.meta.api.objects.Message

/**
 * @author Stanislav Tretyakov
 * 08.03.2021
 */
interface MediaSaver {

    enum class MediaType {
        IMAGE,
        VIDEO,
        GIF
    }

    fun saveMedia(message: Message, path: String): SavedMediaInfo

    fun mediaType(): MediaType

    fun canBeUseful(message: Message): Boolean
}
