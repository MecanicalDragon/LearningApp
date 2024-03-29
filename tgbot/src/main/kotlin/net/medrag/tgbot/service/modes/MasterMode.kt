package net.medrag.tgbot.service.modes

/**
 * @author Stanislav Tretyakov
 * 28.08.2021
 */
enum class MasterMode(val mode: String) {
    SAVE_MEDIA("save"),
    SORT_MEDIA("sort"),
    SHOW_MEDIA("show");

    companion object {
        fun fromString(mode: String?) = when (mode) {
            SAVE_MEDIA.mode -> SAVE_MEDIA
            SORT_MEDIA.mode -> SORT_MEDIA
            SHOW_MEDIA.mode -> SHOW_MEDIA
            else -> throw IllegalArgumentException("Wrong mode type: <$mode>")
        }
    }
}
