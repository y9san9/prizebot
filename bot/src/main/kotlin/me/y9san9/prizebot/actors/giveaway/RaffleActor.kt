package me.y9san9.prizebot.actors.giveaway

import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.actors.storage.participants_storage.ParticipantsStorage
import me.y9san9.random.extensions.shuffledRandomOrg


object RaffleActor {
    suspend fun <T> raffle (
        giveawayId: Long,
        di: T
    ): Boolean where T : ParticipantsStorage, T : GiveawaysStorage {
        val winner = chooseWinner(giveawayId, di) ?: return false
        di.finishGiveaway(giveawayId, winner)
        return true
    }

    private suspend fun <T> chooseWinner (
        giveawayId: Long,
        di: T
    ) where T : ParticipantsStorage =
        di.getParticipantsIds(giveawayId)
        .shuffledRandomOrg()
        .firstOrNull()
}
