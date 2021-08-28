package net.medrag.tgbot.service.modes

import net.medrag.tgbot.service.BotInteractor
import org.telegram.telegrambots.meta.api.objects.Update

/**
 * @author Stanislav Tretyakov
 * 28.08.2021
 */
interface BotMode {
    fun mode(): MasterMode
    fun handleMastersMessage(update: Update, botInteractor: BotInteractor)
}
