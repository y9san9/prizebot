package me.y9san9.telegram.extensions.telegram_bot

import dev.inmo.tgbotapi.CommonAbstracts.plus
import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageEntity.textsources.link
import dev.inmo.tgbotapi.types.MessageEntity.textsources.mention
import dev.inmo.tgbotapi.types.MessageEntity.textsources.regular
import dev.inmo.tgbotapi.types.chat.abstracts.PrivateChat


suspend fun TelegramBot.getUserLink(id: Long, defaultMentionText: String = "") = id.mention (
    parts = try {
        val chat = getChat(ChatId(id)) as PrivateChat
        val lastName = if(chat.lastName.isBlank()) "" else " ${chat.lastName}"
        listOf(regular("${chat.firstName}$lastName".takeIf(String::isNotBlank) ?: defaultMentionText))
    } catch (_: Throwable) {
        regular("This is bug ") + link(url = "https://github.com/y9san9/prizebot/issues/50", text = "#50") +
                " I will will fix it ASAP"
    }
)
