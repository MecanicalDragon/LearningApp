package net.medrag.tgbot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author Stanislav Tretyakov
 * 07.03.2021
 */
@Component
@ConfigurationProperties("net.medrag.tg.bot")
class MasterProps {
    lateinit var token: String
    lateinit var master: String
    lateinit var trusted: List<String>
}
