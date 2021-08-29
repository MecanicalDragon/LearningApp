package net.medrag.tgbot.service.modes

import mu.KotlinLogging
import net.medrag.tgbot.service.BotInteractor
import net.medrag.tgbot.service.mediasaving.SaveMediaHandlingService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

/**
 * @author Stanislav Tretyakov
 * 28.08.2021
 */
@Component
class MediaSavingMode(
    private val saveMediaHandlingService: SaveMediaHandlingService
) : BotMode {

    override fun mode(): MasterMode = MasterMode.SAVE_MEDIA

    override fun activate() = logger.debug { "There is no activation logic in MediaSaving mode, but it's activated." }

    override fun handleMastersMessage(update: Update, botInteractor: BotInteractor) {
        update.message.text?.let {
            saveMediaHandlingService.changeLocation(it)
            logger.info { "Save location has been set to <$it>." }
        }
        saveMediaHandlingService.handleMedia(update.message)?.let {
            botInteractor.downloadMedia(it)
        }
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
