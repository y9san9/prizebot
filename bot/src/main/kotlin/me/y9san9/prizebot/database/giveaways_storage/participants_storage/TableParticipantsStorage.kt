package me.y9san9.prizebot.database.giveaways_storage.participants_storage

import kotlinx.coroutines.Dispatchers
import me.y9san9.prizebot.database.giveaways_storage.participants_storage.TableParticipantsStorage.Participants.PARTICIPANTS_GIVEAWAY_ID
import me.y9san9.prizebot.database.giveaways_storage.participants_storage.TableParticipantsStorage.Participants.PARTICIPANTS_USER_ID
import me.y9san9.extensions.any.unit
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


internal class TableParticipantsStorage(private val database: Database) : ParticipantsStorage {
    private object Participants : Table(name = "participants") {
        val PARTICIPANTS_GIVEAWAY_ID = long("giveawayId")
        val PARTICIPANTS_USER_ID = long("userId")
    }

    init {
        transaction(database) {
            SchemaUtils.create(Participants)
        }
    }

    override suspend fun saveParticipant(giveawayId: Long, userId: Long) = newSuspendedTransaction(Dispatchers.IO, database) {
        Participants.insert {
            it[PARTICIPANTS_GIVEAWAY_ID] = giveawayId
            it[PARTICIPANTS_USER_ID] = userId
        }
    }.unit

    override suspend fun removeParticipant(giveawayId: Long, userId: Long) = newSuspendedTransaction(Dispatchers.IO, database) {
        Participants.deleteWhere {
            (PARTICIPANTS_USER_ID eq userId) and (PARTICIPANTS_GIVEAWAY_ID eq giveawayId)
        }
    }.unit

    override suspend fun getParticipantsIds(giveawayId: Long): List<Long> = newSuspendedTransaction(Dispatchers.IO, database) {
        Participants.selectAll().where { PARTICIPANTS_GIVEAWAY_ID eq giveawayId }
            .map { it[PARTICIPANTS_USER_ID] }
    }

    override suspend fun getParticipantsCount(giveawayId: Long) = newSuspendedTransaction(Dispatchers.IO, database) {
        Participants
            .selectAll().where { PARTICIPANTS_GIVEAWAY_ID eq giveawayId }
            .count()
            .toInt()
    }

    override suspend fun isParticipant(giveawayId: Long, userId: Long) = newSuspendedTransaction(Dispatchers.IO, database) {
        Participants
            .selectAll().where { (PARTICIPANTS_GIVEAWAY_ID eq giveawayId) and (PARTICIPANTS_USER_ID eq userId) }
            .firstOrNull() != null
    }
}

