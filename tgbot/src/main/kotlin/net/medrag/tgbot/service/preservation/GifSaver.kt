package net.medrag.tgbot.service.preservation

import net.medrag.tgbot.model.preservation.SavedMediaInfo
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.GetFile
import org.telegram.telegrambots.meta.api.objects.Message
import java.io.File

/**
 * @author Stanislav Tretyakov
 * 03.08.2021
 */
@Service
class GifSaver : MediaSaver {

    override fun saveMedia(message: Message, path: String): SavedMediaInfo {
        val getFile = GetFile(message.animation.fileId)
        val file = File(path + "/gif/" + message.animation.fileUniqueId + ".gif.mp4")
        return SavedMediaInfo(getFile, file)
    }

    override fun mediaType() = MediaSaver.MediaType.GIF

    override fun canBeUseful(message: Message) = message.hasAnimation()
}
