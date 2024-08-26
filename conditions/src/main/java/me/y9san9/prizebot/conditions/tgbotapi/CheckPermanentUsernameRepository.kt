package me.y9san9.prizebot.conditions.tgbotapi

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.extensions.utils.whenUsernameChat
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.RawChatId
import dev.inmo.tgbotapi.utils.PreviewFeature

class CheckPermanentUsernameRepository(
    private val cacheTime: Long = 10_000,
    private val bot: TelegramBot
) {
    private val inMemoryCache = mutableListOf<CachedUsername>()

    suspend fun checkChannelUsername(
        channelId: Long,
        username: String
    ): Boolean {
        val inMemoryUsername = inMemoryCache
            .firstOrNull { (cachedChannelId, _, _) ->
                cachedChannelId == channelId
            }?.takeIf { inMemoryUsername ->
                val expired = System.currentTimeMillis() - cacheTime > inMemoryUsername.cachedUnixMillis

                if (expired) {
                    inMemoryCache.removeAll { it.id == inMemoryUsername.id }
                }

                return@takeIf expired
            } ?: loadChannelAndSaveToCache(channelId)


        return inMemoryUsername?.username == username
    }

    @OptIn(PreviewFeature::class)
    private suspend fun loadChannelAndSaveToCache(channelId: Long): CachedUsername? {
        val username = runCatching {
            bot.getChat(ChatId(RawChatId(channelId))).whenUsernameChat { chat ->
                val username = chat.username?.username ?: return@whenUsernameChat null
                CachedUsername(channelId, username, System.currentTimeMillis())
            }
        }.getOrNull() ?: return null

        inMemoryCache += username

        return username
    }

    private data class CachedUsername(val id: Long, val username: String, val cachedUnixMillis: Long)
}
