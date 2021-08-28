package net.medrag.tgbot.model.mediasaving

import org.telegram.telegrambots.meta.api.methods.GetFile
import java.io.File

/**
 * @author Stanislav Tretyakov
 * 08.03.2021
 */
data class SaveMediaInfo(
    val telegramFileUri: GetFile,
    val downloadedFile: File
)
