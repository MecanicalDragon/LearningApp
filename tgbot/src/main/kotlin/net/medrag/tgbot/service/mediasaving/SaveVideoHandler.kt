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
class SaveVideoHandler : SaveMediaHandler {

    override fun handleMedia(message: Message, path: String): SaveMediaInfo {
        val getFile = GetFile(message.video.fileId)
        val file = File(fileName(path, message.video.fileUniqueId))
        return SaveMediaInfo(getFile, file)
    }

    override fun mediaType() = SaveMediaHandler.MediaType.VIDEO

    override fun canBeUseful(message: Message) = message.hasVideo()

    override fun extensionString(): String = ".mp4"

    override fun mediaPrefix(): String = "VID"
}
