package me.y9san9.prizebot.database.giveaways_active_messages_storage

import dev.inmo.tgbotapi.types.InlineMessageIdentifier
import me.y9san9.prizebot.resources.ACTIVE_MESSAGES_LIMIT
import org.jetbrains.exposed.sql.Database


fun GiveawaysActiveMessagesStorage(database: Database): GiveawaysActiveMessagesStorage =
    TableGiveawaysActiveMessagesStorage(database)

interface GiveawaysActiveMessagesStorage {
    /**
     * Should add to storage new message id and truncate messages to size [ACTIVE_MESSAGES_LIMIT]
     */
    fun addActiveMessage(giveawayId: Long, inlineMessageId: InlineMessageIdentifier)

    fun getActiveMessage(giveawayId: Long): List<InlineMessageIdentifier>
}
