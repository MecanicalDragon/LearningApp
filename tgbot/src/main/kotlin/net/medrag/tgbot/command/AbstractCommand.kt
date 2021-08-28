package net.medrag.tgbot.command

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand

/**
 * @author Stanislav Tretyakov
 * 08.02.2021
 */
abstract class AbstractCommand(command: String, description: String) : BotCommand(command, description)
