package me.y9san9.prizebot.database.giveaways_active_messages_storage

import dev.inmo.tgbotapi.types.InlineMessageId
import kotlinx.coroutines.Dispatchers
import me.y9san9.extensions.any.unit
import me.y9san9.prizebot.database.giveaways_active_messages_storage.TableGiveawaysActiveMessagesStorage.Storage.GIVEAWAY_ID
import me.y9san9.prizebot.database.giveaways_active_messages_storage.TableGiveawaysActiveMessagesStorage.Storage.LAST_UPDATE_TIME
import me.y9san9.prizebot.database.giveaways_active_messages_storage.TableGiveawaysActiveMessagesStorage.Storage.MESSAGE_ID
import me.y9san9.prizebot.resources.ACTIVE_MESSAGES_LIMIT
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


class TableGiveawaysActiveMessagesStorage(private val database: Database) : GiveawaysActiveMessagesStorage {
    private object Storage : Table(name = "giveaways_active_messages") {
        val ROW_ID = long("rowId").autoIncrement()
        val GIVEAWAY_ID = long("giveawayId")
        val MESSAGE_ID = text("inlineMessageId")
        val LAST_UPDATE_TIME = long("lastUpdateTime")
    }

    init {
        transaction(database) {
            SchemaUtils.create(Storage)
        }
    }

    override suspend fun addActiveMessage (
        giveawayId: Long, inlineMessage: GiveawaysActiveMessagesStorage.Message
    ) = newSuspendedTransaction(Dispatchers.IO, database) {
        Storage.insert {
            it[GIVEAWAY_ID] = giveawayId
            it[MESSAGE_ID] = inlineMessage.id.string
            it[LAST_UPDATE_TIME] = inlineMessage.lastUpdateTime
        }

        Storage.deleteWhere {
            MESSAGE_ID inList Storage.selectAll()
                .where { GIVEAWAY_ID eq giveawayId }
                .orderBy(ROW_ID, SortOrder.DESC)
                .limit(Int.MAX_VALUE, offset = ACTIVE_MESSAGES_LIMIT.toLong())
                .map { it[MESSAGE_ID] }
        }
    }.unit

    override suspend fun getActiveMessages(giveawayId: Long): List<GiveawaysActiveMessagesStorage.Message> =
        newSuspendedTransaction(Dispatchers.IO, database) {
            Storage.selectAll().where { GIVEAWAY_ID eq giveawayId }.map {
                GiveawaysActiveMessagesStorage.Message(
                    id = InlineMessageId(it[MESSAGE_ID]),
                    lastUpdateTime = it[LAST_UPDATE_TIME]
                )
            }
        }

    override suspend fun setLastUpdated(id: InlineMessageId, lastUpdateTime: Long) {
        newSuspendedTransaction(Dispatchers.IO, database) {
            Storage.update({ MESSAGE_ID eq id.string }) {
                it[LAST_UPDATE_TIME] = lastUpdateTime
            }
        }
    }
}