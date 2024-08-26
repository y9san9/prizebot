package me.y9san9.telegram.extensions.telegram_bot

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.types.message.textsources.mention
import dev.inmo.tgbotapi.types.toChatId

suspend fun TelegramBot.getUserLinkOrNull(id: Long, defaultMentionText: String = "") = mention (
    text = getUserTitleOrNull(id) ?: defaultMentionText,
    userId = id.toChatId()
)
