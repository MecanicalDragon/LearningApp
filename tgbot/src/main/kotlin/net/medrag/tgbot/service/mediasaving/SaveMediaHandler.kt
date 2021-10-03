package net.medrag.tgbot.service.mediasaving

import net.medrag.tgbot.model.mediasaving.SaveMediaInfo
import org.telegram.telegrambots.meta.api.objects.Message
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    fun extensionString(): String

    fun mediaPrefix(): String

    fun fileName(path: String, mediaName: String) =
        path + "/" + mediaPrefix() + "_" + LocalDateTime.now().format(FORMATTER) + mediaName + "_" + extensionString()
}

val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss_")
