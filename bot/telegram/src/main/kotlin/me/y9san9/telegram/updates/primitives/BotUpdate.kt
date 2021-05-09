package me.y9san9.telegram.updates.primitives

import dev.inmo.tgbotapi.bot.TelegramBot


interface BotUpdate {
    val bot: TelegramBot
}
