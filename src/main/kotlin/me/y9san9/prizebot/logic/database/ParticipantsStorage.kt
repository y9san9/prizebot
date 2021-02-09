package me.y9san9.prizebot.logic.database

import com.kotlingang.kds.mutate
import kotlinx.serialization.Serializable
import me.y9san9.prizebot.logic.database.TableParticipantsStorage.Participants.PARTICIPANTS_GIVEAWAY_ID
import me.y9san9.prizebot.logic.database.TableParticipantsStorage.Participants.PARTICIPANTS_USER_ID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


@Serializable
internal data class Participant (
    val giveawayId: Long,
    val userId: Long
)

interface ParticipantsStorage {
    fun saveParticipant(giveawayId: Long, userId: Long)
    fun getParticipantsIds(giveawayId: Long): List<Long>
    fun getParticipantsCount(giveawayId: Long): Int
    fun isParticipant(giveawayId: Long, userId: Long): Boolean
}

fun ParticipantsStorage(database: Database?): ParticipantsStorage =
    if(database == null)
        KDSParticipantsStorage()
    else
        TableParticipantsStorage(database)

private class KDSParticipantsStorage : ParticipantsStorage {
    override fun saveParticipant(giveawayId: Long, userId: Long) = KDS.mutate {
        participants += Participant(giveawayId, userId)
    }

    override fun getParticipantsIds(giveawayId: Long): List<Long> = KDS.participants
        .filter { it.giveawayId == giveawayId }
        .map(Participant::userId)

    override fun getParticipantsCount(giveawayId: Long) = KDS.participants
        .filter { it.giveawayId == giveawayId }
        .size

    override fun isParticipant(giveawayId: Long, userId: Long) = KDS.participants
        .firstOrNull { it.giveawayId == giveawayId && it.userId == userId } != null
}

private class TableParticipantsStorage(private val database: Database) : ParticipantsStorage {
    private object Participants : Table(name = "participants") {
        val PARTICIPANTS_GIVEAWAY_ID = long("giveawayId")
        val PARTICIPANTS_USER_ID = long("userId")
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
