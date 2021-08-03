package net.medrag.tgbot.service.preservation

import net.medrag.tgbot.model.preservation.SavedMediaInfo
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.GetFile
import org.telegram.telegrambots.meta.api.objects.Message
import java.io.File

/**
 * @author Stanislav Tretyakov
 * 08.03.2021
 */
@Service
class ImageSaver : MediaSaver {

    override fun saveMedia(message: Message, path: String): SavedMediaInfo {
        val photo = message.photo.last()
        val getFile = GetFile(photo.fileId)
        val file = File(path + "/img/" + photo.fileUniqueId + ".jpg")
        return SavedMediaInfo(getFile, file)
    }

    override fun mediaType() = MediaSaver.MediaType.IMAGE

    override fun canBeUseful(message: Message) = message.hasPhoto()
}
