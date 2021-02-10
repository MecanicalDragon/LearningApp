package net.medrag.tgbot.util

import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User


/**
 * @author Stanislav Tretyakov
 * 09.02.2021
 */
fun Chat?.idString() = this?.id?.toString() ?: "0"

fun User?.username() = this?.firstName ?: this?.lastName ?: this?.userName ?: "незнакомец"

fun Update?.callbackPrefix() = this?.callbackQuery?.data?.split(CALLBACK_DELIMITER)?.get(0) ?: INVALID_CALLBACK_PREFIX

fun Update?.chatIdFromCallback() = this?.callbackQuery?.message?.chatId?.toString() ?: "0"

fun Update?.messageIdFromCallback() = this?.callbackQuery?.message?.messageId ?: 0

fun Update?.userFromCallback() = this?.callbackQuery?.from