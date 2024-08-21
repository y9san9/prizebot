package me.y9san9.prizebot.database.giveaways_active_messages_storage

import dev.inmo.tgbotapi.types.InlineMessageId
import me.y9san9.prizebot.resources.ACTIVE_MESSAGES_LIMIT
import org.jetbrains.exposed.sql.Database


fun GiveawaysActiveMessagesStorage(database: Database): GiveawaysActiveMessagesStorage =
    TableGiveawaysActiveMessagesStorage(database)

interface GiveawaysActiveMessagesStorage {
    /**
     * Should add to storage new message id and truncate messages to size [ACTIVE_MESSAGES_LIMIT]
     */
    suspend fun addActiveMessage(giveawayId: Long, inlineMessage: Message)

    suspend fun getActiveMessages(giveawayId: Long): List<Message>

    suspend fun setLastUpdated(id: InlineMessageId, lastUpdateTime: Long)

    data class Message(val id: InlineMessageId, val lastUpdateTime: Long)
}
