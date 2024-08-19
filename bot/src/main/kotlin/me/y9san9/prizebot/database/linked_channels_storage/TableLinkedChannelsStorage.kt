package me.y9san9.prizebot.database.linked_channels_storage

import kotlinx.coroutines.Dispatchers
import me.y9san9.prizebot.database.linked_channels_storage.TableLinkedChannelsStorage.LinkedChannels.CHANNEL_ID
import me.y9san9.prizebot.database.linked_channels_storage.TableLinkedChannelsStorage.LinkedChannels.USER_ID
import me.y9san9.extensions.any.unit
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


internal class TableLinkedChannelsStorage (
    private val database: Database
) : LinkedChannelsStorage {
    private object LinkedChannels : Table(name = "linked_channels") {
        val CHANNEL_ID = long("channelId")
        val USER_ID = long("userId")
    }

    init {
        transaction(database) {
            SchemaUtils.create(LinkedChannels)
        }
    }

    override suspend fun linkChannel(userId: Long, channelId: Long) = newSuspendedTransaction(Dispatchers.IO, database) {
        if(LinkedChannels.selectAll().where { (CHANNEL_ID eq channelId) and (USER_ID eq userId) }.firstOrNull() == null)
            LinkedChannels.insert {
                it[USER_ID] = userId
                it[CHANNEL_ID] = channelId
            }
    }.unit

    override suspend fun unlinkChannel(userId: Long, channelId: Long) = newSuspendedTransaction(Dispatchers.IO, database) {
        LinkedChannels.deleteWhere { (USER_ID eq userId) and (CHANNEL_ID eq channelId) }
    }.unit

    override suspend fun getChannels(userId: Long): List<Long> = newSuspendedTransaction(Dispatchers.IO, database) {
        LinkedChannels.selectAll().where { USER_ID eq userId }.map { it[CHANNEL_ID] }
    }
}
