package net.medrag.tgbot.model.preservation

import org.telegram.telegrambots.meta.api.methods.GetFile
import java.io.File

/**
 * @author Stanislav Tretyakov
 * 08.03.2021
 */
data class SavedMediaInfo(
    val telegramFileUri: GetFile,
    val downloadedFile: File
)
