package me.y9san9.telegram.extensions.telegram_bot

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.chat.PrivateChat


suspend fun TelegramBot.getUserTitleOrNull(id: Long): String? {
    val chat = try {
        getChat(ChatId(id)) as PrivateChat
    } catch (_: Throwable) {
        return null
    }
    return "${chat.firstName}${if(chat.lastName.isBlank()) "" else " ${chat.lastName}"}"
        .takeIf(String::isNotBlank)
}
