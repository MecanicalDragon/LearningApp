package net.medrag.tgbot.service

import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import net.medrag.tgbot.service.modes.BotMode
import net.medrag.tgbot.service.modes.MasterMode
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

/**
 * @author Stanislav Tretyakov
 * 03.08.2021
 */
@Service
class MasterMessageHandler(botModes: List<BotMode>) {

    private val modes = botModes.associateBy { it.mode() }

    private var currentMode: BotMode = modes[MasterMode.SAVE_MEDIA]
        ?: throw IllegalStateException("Incorrect bot mode has been picked.")

    fun handleMastersMessage(update: Update, botInteractor: BotInteractor) =
        currentMode.handleMastersMessage(update, botInteractor)

    fun switchMode(mode: String?) = with(MasterMode.fromString(mode)) {
        currentMode = modes[this]
            ?: throw IllegalArgumentException("No mode <$this> has been found among available modes.")
        currentMode.activate()
        logger.info { "Mode <$this> has been set." }
    }

    @PostConstruct
    fun init() {
        currentMode.activate()
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
