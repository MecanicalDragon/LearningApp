package net.medrag.tgbot.service.modes

import mu.KotlinLogging
import net.medrag.tgbot.service.BotInteractor
import net.medrag.tgbot.util.chatIdFromMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.name

/**
 * @author Stanislav Tretyakov
 * 28.08.2021
 */
@Component
class SortingMode(
    @Value("\${net.medrag.tg.bot.sorting.dir}")
    private val sortingDir: String,
    @Value("\${net.medrag.tg.bot.preservation.dir}")
    private val preservationDir: String
) : BotMode {

    private val source: Path = Path.of(sortingDir)
    private val output: Path = Path.of(preservationDir)
    lateinit var media: List<Path>
    lateinit var current: Path
    lateinit var iterator: Iterator<Path>

    override fun mode(): MasterMode = MasterMode.SORT_MEDIA

    override fun activate() {
        try {
            media = Files.walk(source, 1).toList()
            iterator = media.iterator()
            current = iterator.next()
            logger.info { "Sorting mode activated. Media library size is ${media.size - 1}." }
        } catch (e: NoSuchFileException) {
            logger.error { e }
            throw e
        }
    }

    // TODO: fix this algorithm
    override fun handleMastersMessage(update: Update, botInteractor: BotInteractor) {

        if (media.size < 2) {
            logger.warn("Nothing to sort!")
            botInteractor.respond(update, "Nothing to sort!")
        } else {
            if (!current.isDirectory()) {
                moveCurrentFile(update.message.text)
                current = iterator.next()
            }
            while (iterator.hasNext() && current.isDirectory()) {
                current = iterator.next()
            }
            if (!current.isDirectory()) {
                if (current.name.endsWith(".gif")) {
                    SendAnimation().apply {
                        chatId = update.chatIdFromMessage()
                        animation = InputFile(current.toFile())
                    }.also {
                        botInteractor.getSender().execute(it)
                    }
                } else {
                    SendPhoto().apply {
                        chatId = update.chatIdFromMessage()
                        photo = InputFile(current.toFile())
                    }.also {
                        botInteractor.getSender().execute(it)
                    }
                }
            }
        }
    }

    private fun moveCurrentFile(path: String?) {
        try {
            val newPath = output.resolve(Path.of(path)).also {
                if (!Files.exists(it)) Files.createDirectory(it)
            }
            Files.copy(current, newPath.resolve(current.fileName))
            Files.delete(current)
            logger.info { "File <$current> has moved to: <$newPath>." }
        } catch (e: Exception) {
            logger.error { e }
        }
    }

    companion object {
        const val DEAD_END_MESSAGE = "Looks like there is no more non-dir files in directory."
        private val logger = KotlinLogging.logger { }
    }
}
