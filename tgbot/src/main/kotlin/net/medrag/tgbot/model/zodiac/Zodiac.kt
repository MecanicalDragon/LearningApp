package net.medrag.tgbot.model.zodiac


/**
 * @author Stanislav Tretyakov
 * 07.02.2021
 */
enum class Zodiac(
        val emoji: String,
        val startMonth: Int,
        val startDay: Int,
        val endMonth: Int,
        val endDay: Int
) {
    CAPRICORN("\u2651", 12, 21, 1, 20),
    AQUARIUS("\u2652", 1, 21, 2, 19),
    PISCES("\u2653", 2, 20, 3, 20),
    ARIES("\u2648", 3, 21, 4, 19),
    TAURUS("\u2649", 4, 20, 5, 20),
    GEMINI("\u264A", 5, 21, 6, 21),
    CANCER("\u264B", 6, 22, 7, 23),
    LEO("\u264C", 7, 24, 8, 23),
    VIRGO("\u264D", 8, 24, 9, 22),
    LIBRA("\u264E", 9, 23, 10, 22),
    SCORPIO("\u264F", 10, 23, 11, 22),
    SAGITTARIUS("\u2650", 11, 23, 12, 20);
}