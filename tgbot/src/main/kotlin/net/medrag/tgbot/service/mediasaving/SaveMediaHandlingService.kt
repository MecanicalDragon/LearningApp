package net.medrag.tgbot.service.mediasaving

import jakarta.annotation.PostConstruct
import net.medrag.tgbot.model.mediasaving.SaveMediaInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Message
import java.util.concurrent.atomic.AtomicReference

/**
 * @author Stanislav Tretyakov
 * 08.03.2021
 */
@Service
class SaveMediaHandlingService(
    @Value("\${net.medrag.tg.bot.preservation.dir}")
    val rootLocation: String,
    val saveMediaHandlers: Set<SaveMediaHandler>
) {
    val location: AtomicReference<String> = AtomicReference("")

    fun handleMedia(message: Message): SaveMediaInfo? {
        for (mediaHandler in saveMediaHandlers) {
            if (mediaHandler.canBeUseful(message)) {
                return mediaHandler.handleMedia(message, location.get())
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
