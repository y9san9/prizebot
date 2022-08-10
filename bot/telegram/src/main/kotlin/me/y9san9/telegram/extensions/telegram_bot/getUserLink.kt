package me.y9san9.telegram.extensions.telegram_bot

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.types.message.textsources.mention


suspend fun TelegramBot.getUserLink(id: Long, defaultMentionText: String = "") = id.mention (
    text = getUserTitle(id) ?: defaultMentionText
)
