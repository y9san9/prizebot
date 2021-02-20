package me.y9san9.prizebot.actors.storage.participants_storage

import me.y9san9.prizebot.actors.storage.participants_storage.TableParticipantsStorage.Participants.PARTICIPANTS_GIVEAWAY_ID
import me.y9san9.prizebot.actors.storage.participants_storage.TableParticipantsStorage.Participants.PARTICIPANTS_USER_ID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


class TableParticipantsStorage(private val database: Database) : ParticipantsStorage {
    private object Participants : Table(name = "participants") {
        val PARTICIPANTS_GIVEAWAY_ID = long("giveawayId")
        val PARTICIPANTS_USER_ID = long("userId")
    }

    init {
        transaction(database) {
            SchemaUtils.create(Participants)
        }
    }

    override fun saveParticipant(giveawayId: Long, userId: Long) = transaction(database) {
        Participants.insert {
            it[PARTICIPANTS_GIVEAWAY_ID] = giveawayId
            it[PARTICIPANTS_USER_ID] = userId
        }
    }.let { }

    override fun getParticipantsIds(giveawayId: Long): List<Long> = transaction(database) {
        Participants.select { PARTICIPANTS_GIVEAWAY_ID eq giveawayId }
            .map { it[PARTICIPANTS_USER_ID] }
    }

    override fun getParticipantsCount(giveawayId: Long) = transaction(database) {
        Participants
            .select { PARTICIPANTS_GIVEAWAY_ID eq giveawayId }
            .count()
            .toInt()
    }

    override fun isParticipant(giveawayId: Long, userId: Long) = transaction(database) {
        Participants
            .select { (PARTICIPANTS_GIVEAWAY_ID eq giveawayId) and (PARTICIPANTS_USER_ID eq userId) }
            .firstOrNull() != null
    }
}

