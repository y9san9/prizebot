package me.y9san9.telegram.extensions.telegram_bot

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageEntity.textsources.mention
import dev.inmo.tgbotapi.types.chat.abstracts.PrivateChat


suspend fun TelegramBot.getUserLink(id: Long, defaultMentionText: String = "") = id.mention (
    text = try {
        val chat = getChat(ChatId(id)) as PrivateChat
        val lastName = if(chat.lastName.isBlank()) "" else " ${chat.lastName}"
        "${chat.firstName}$lastName".takeIf(String::isNotBlank) ?: defaultMentionText
    } catch (_: Throwable) {
        // Telegram bug
        "User"
    }
)
