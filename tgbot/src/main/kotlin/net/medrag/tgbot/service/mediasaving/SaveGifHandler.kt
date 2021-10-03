package net.medrag.tgbot.service.mediasaving

import net.medrag.tgbot.model.mediasaving.SaveMediaInfo
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.GetFile
import org.telegram.telegrambots.meta.api.objects.Message
import java.io.File

/**
 * @author Stanislav Tretyakov
 * 03.08.2021
 */
@Service
class SaveGifHandler : SaveMediaHandler {

    override fun handleMedia(message: Message, path: String): SaveMediaInfo {
        val getFile = GetFile(message.animation.fileId)
        val file = File(fileName(path, message.animation.fileUniqueId))
        return SaveMediaInfo(getFile, file)
    }

    override fun mediaType() = SaveMediaHandler.MediaType.GIF

    override fun canBeUseful(message: Message) = message.hasAnimation()

    override fun extensionString(): String = ".gif.mp4"

    override fun mediaPrefix(): String = "GIF"
}
