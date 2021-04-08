package me.y9san9.telegram.extensions.telegram_bot

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageEntity.textsources.mention
import dev.inmo.tgbotapi.types.chat.abstracts.PrivateChat


suspend fun TelegramBot.getUserLink(id: Long, defaultMentionText: String = "") = id.mention (
    text = (getChat(ChatId(id)) as PrivateChat).let {
        "${it.firstName} ${it.lastName}".takeIf(String::isNotBlank) ?: defaultMentionText
    }
)
