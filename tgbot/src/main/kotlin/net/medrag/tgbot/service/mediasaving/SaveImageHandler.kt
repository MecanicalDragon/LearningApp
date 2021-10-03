package net.medrag.tgbot.service.mediasaving

import net.medrag.tgbot.model.mediasaving.SaveMediaInfo
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.GetFile
import org.telegram.telegrambots.meta.api.objects.Message
import java.io.File

/**
 * @author Stanislav Tretyakov
 * 08.03.2021
 */
@Service
class SaveImageHandler : SaveMediaHandler {

    override fun handleMedia(message: Message, path: String): SaveMediaInfo {
        val photo = message.photo.last()
        val getFile = GetFile(photo.fileId)
        val file = File(fileName(path, photo.fileUniqueId))
        return SaveMediaInfo(getFile, file)
    }

    override fun mediaType() = SaveMediaHandler.MediaType.IMAGE

    override fun canBeUseful(message: Message) = message.hasPhoto()

    override fun extensionString(): String = ".jpg"

    override fun mediaPrefix(): String = "IMG"
}
