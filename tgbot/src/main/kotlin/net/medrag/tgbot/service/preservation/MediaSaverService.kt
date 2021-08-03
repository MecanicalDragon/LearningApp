package net.medrag.tgbot.service.preservation

import net.medrag.tgbot.model.preservation.SavedMediaInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Message
import java.util.concurrent.atomic.AtomicReference
import javax.annotation.PostConstruct

/**
 * @author Stanislav Tretyakov
 * 08.03.2021
 */
@Service
class MediaSaverService(
    @Value("\${net.medrag.tg.bot.preservation.dir}")
    val rootLocation: String,
    val mediaSavers: Set<MediaSaver>
) {
    val location: AtomicReference<String> = AtomicReference("")

    fun saveMedia(message: Message): SavedMediaInfo? {
        for (mediaSaver in mediaSavers) {
            if (mediaSaver.canBeUseful(message)) {
                return mediaSaver.saveMedia(message, location.get())
            }
        }
        return null
    }

    fun changeLocation(dir: String) {
        location.set(rootLocation + dir.lowercase())
    }

    @PostConstruct
    fun init() {
        location.set(rootLocation + "default")
    }
}
