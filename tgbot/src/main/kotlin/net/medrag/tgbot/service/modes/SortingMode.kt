package net.medrag.tgbot.service.modes

import mu.KotlinLogging
import net.medrag.tgbot.service.BotInteractor
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

/**
 * @author Stanislav Tretyakov
 * 28.08.2021
 */
@Component
class SortingMode : BotMode {

    override fun mode(): MasterMode = MasterMode.SORT_MEDIA

    override fun handleMastersMessage(update: Update, botInteractor: BotInteractor) {
        logger.error { "NOT IMPLEMENTED YET!!" }
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
