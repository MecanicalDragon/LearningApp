package net.medrag.tgbot.command

import net.medrag.tgbot.service.MasterMessageHandler
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

/**
 * @author Stanislav Tretyakov
 * 28.08.2021
 */
@Component
class SwitchingModeCommand(
    private val masterMessageHandler: MasterMessageHandler
) : AbstractCommand("mode", "switches modes for bot master") {

    override fun execute(p0: AbsSender?, p1: User?, p2: Chat?, p3: Array<out String>?) =
        masterMessageHandler.switchMode(p3?.get(0))
}
