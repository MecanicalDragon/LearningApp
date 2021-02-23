package net.medrag.tgbot.service

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


/**
 * @author Stanislav Tretyakov
 * 23.02.2021
 */
@Component
@ConfigurationProperties("net.medrag.bot.post")
class PostProps {
    lateinit var scheduled: String
    lateinit var source: String
    lateinit var posted: String
    lateinit var chatId: String
}