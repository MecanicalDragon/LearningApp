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
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.name
import kotlin.streams.asSequence

/**
 * @author Stanislav Tretyakov
 * 28.08.2021
 */
@Component
class ShowMode(
    @Value("\${net.medrag.tg.bot.preservation.dir}")
    private val sourceDir: String
) : BotMode {

    private lateinit var source: Path
    private lateinit var media: List<Path>
    private lateinit var iterator: Iterator<Path>

    private var current: Path? = null

    override fun mode(): MasterMode = MasterMode.SHOW_MEDIA

    override fun activate() {
        setSource("sort/")
    }

    override fun handleMastersMessage(update: Update, botInteractor: BotInteractor) {
        try {
            val message = update.message.text
            if (message == ".") {
                giveNext(update, botInteractor)
            } else if (message.startsWith(".")) {
                setSource("${message.substring(1)}/")
                giveNext(update, botInteractor)
            } else {
                moveCurrentFileTo(message)
                giveNext(update, botInteractor)
            }
        } catch (e: Exception) {
            logger.error { e }
            botInteractor.respond(update, e.message ?: e.toString())
        }
    }

    private fun setSource(message: String) {
        source = Path.of(sourceDir + message)
        media = Files.walk(source, 1).asSequence().filter { !it.isDirectory() }.shuffled().toList()
        iterator = media.iterator()
    }

    private fun giveNext(update: Update, botInteractor: BotInteractor) {
        if (iterator.hasNext()) {
            current = iterator.next()
            sendFile(update, botInteractor)
        } else {
            logger.warn("Nothing to show in dir <$source>")
            botInteractor.respond(update, "Nothing to show in dir <$source>")
        }
    }

    private fun moveCurrentFileTo(targetDir: String) {
        current?.let {
            val newPath = Path.of("$sourceDir$targetDir/").also {
                if (!Files.exists(it)) Files.createDirectory(it)
            }
            Files.copy(it, newPath.resolve(it.fileName))
            Files.delete(it)
            logger.info { "File <$it> has moved to: <$newPath>." }
        }
    }

    private fun sendFile(update: Update, botInteractor: BotInteractor) {
        current?.let { path ->
            val ext = path.name.lowercase()
            if (ext.endsWith(".mp4")) {
                moveCurrentFileTo("mp4")
                botInteractor.respond(update, "mp4 file <$path> was moved to mp4 directory.")
            } else if (ext.endsWith(".gif")) {
                SendAnimation().apply {
                    chatId = update.chatIdFromMessage()
                    animation = InputFile(path.toFile())
                }.also {
                    botInteractor.getSender().execute(it)
                }
            } else {
                SendPhoto().apply {
                    chatId = update.chatIdFromMessage()
                    photo = InputFile(path.toFile())
                }.also {
                    botInteractor.getSender().execute(it)
                }
            }
        } ?: logger.error { "Current file is not defined." }
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
