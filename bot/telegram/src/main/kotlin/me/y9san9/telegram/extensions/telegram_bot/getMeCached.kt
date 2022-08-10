package me.y9san9.telegram.extensions.telegram_bot

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.bot.getMe
import dev.inmo.tgbotapi.types.chat.ExtendedBot
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


private var bot: ExtendedBot? = null
private val mutex = Mutex()

suspend fun TelegramBot.getMeCached() = mutex.withLock {
    if(bot == null)
        bot = getMe()
    return@withLock bot ?: error("Concurrent exception")
}
