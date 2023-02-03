package me.y9san9.telegram.extensions.telegram_bot

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.types.message.textsources.mention


suspend fun TelegramBot.getUserLinkOrNull(id: Long, defaultMentionText: String = "") = id.mention (
    text = getUserTitleOrNull(id) ?: defaultMentionText
)
