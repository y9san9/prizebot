package me.y9san9.prizebot.actors.storage.participants_storage

import com.kotlingang.kds.mutate
import me.y9san9.prizebot.actors.storage.kds.KDS


class KDSParticipantsStorage : ParticipantsStorage {
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
