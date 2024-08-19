package me.y9san9.prizebot.database.giveaways_storage.participants_storage

import org.jetbrains.exposed.sql.Database


internal fun ParticipantsStorage(database: Database): ParticipantsStorage =
    TableParticipantsStorage(database)

internal interface ParticipantsStorage {
    suspend fun saveParticipant(giveawayId: Long, userId: Long)
    suspend fun removeParticipant(giveawayId: Long, userId: Long)
    suspend fun getParticipantsIds(giveawayId: Long): List<Long>
    suspend fun getParticipantsCount(giveawayId: Long): Int
    suspend fun isParticipant(giveawayId: Long, userId: Long): Boolean
}
