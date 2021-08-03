package net.medrag.tgbot.service

import mu.KotlinLogging
import net.medrag.tgbot.service.preservation.MediaSaverService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException

/**
 * @author Stanislav Tretyakov
 * 03.08.2021
 */
@Service
class MasterMessageHandler(
    private val mediaSaverService: MediaSaverService
) {
    fun handleMasterRequest(update: Update, bot: TelegramLongPollingCommandBot) {
        val text = update.message.text

        if (text != null) {
            mediaSaverService.changeLocation(text)
            logger.info { "Save location has been set to '$text'." }
        }

        mediaSaverService.saveMedia(update.message)?.let {
            try {
                val filePath = bot.execute(it.telegramFileUri).filePath
                val file = bot.downloadFile(filePath, it.downloadedFile)
                logger.info("downloaded: ${file.absolutePath}")
            } catch (e: TelegramApiRequestException) {
                logger.error { "Media file failed to be downloaded. Reason: $e" }
            }
        }
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
