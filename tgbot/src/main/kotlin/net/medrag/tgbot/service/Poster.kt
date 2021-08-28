package net.medrag.tgbot.service

import mu.KotlinLogging
import net.medrag.tgbot.config.PostProps
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.methods.send.SendVideo
import org.telegram.telegrambots.meta.api.objects.InputFile
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.name
import kotlin.streams.toList

/**
 * @author Stanislav Tretyakov
 * 23.02.2021
 */
@Service
@ConditionalOnProperty("net.medrag.bot.post.enable", matchIfMissing = true)
class Poster(
    var postProps: PostProps,
    var bot: MedragBot
) {

    @Scheduled(cron = "\${net.medrag.bot.post.schedule}")
    fun post() {

        val media: List<Path> = Files.walk(Path.of(postProps.source), 1).toList()
        if (media.size == 1) {
            logger.info("Nothing to post!")
            return
        }
        var random = media.random()
        while (random.isDirectory()) {
            random = media.random()
        }

        when (random.name.substringAfterLast(".").toUpperCase()) {
            "JPG", "PNG" -> sendPhoto(random)
            "MP4" -> sendVideo(random)
            else -> logger.warn("Unrecognized format file: $random")
        }
    }

    private fun sendVideo(path: Path) {
        SendVideo().apply {
            this.chatId = postProps.chatId
            this.video = InputFile(path.toFile())
        }.also {
            bot.execute(it)
        }
        logger.info("Video $path has been posted.")
        removeFileFromToPostDir(path)
    }

    private fun sendPhoto(path: Path) {
        SendPhoto().apply {
            this.chatId = postProps.chatId
            this.photo = InputFile(path.toFile())
        }.also {
            bot.execute(it)
        }
        logger.info("Photo $path has been posted.")
        removeFileFromToPostDir(path)
    }

    private fun removeFileFromToPostDir(path: Path) {
        Files.copy(path, Path.of(postProps.posted, path.name))
        Files.delete(path)
        logger.info("File $path has been replaced successfully.")
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
