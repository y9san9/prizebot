package me.y9san9.prizebot.actors.giveaway

import me.y9san9.prizebot.actors.storage.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.actors.storage.participants_storage.ParticipantsStorage
import me.y9san9.random.extensions.shuffledRandomOrg


object RaffleActor {
    suspend fun <T> raffle (
        giveaway: ActiveGiveaway,
        di: T
    ): Boolean where T : ParticipantsStorage, T : GiveawaysStorage {
        val winnerIds = chooseWinners(giveaway, di) ?: return false
        di.finishGiveaway(giveaway.id, winnerIds)
        return true
    }

    private suspend fun <T> chooseWinners (
        giveaway: ActiveGiveaway,
        di: T
    ) where T : ParticipantsStorage =
        di.getParticipantsIds(giveaway.id)
            .shuffledRandomOrg()
            .take(giveaway.winnersCount.value)
            .takeIf { it.size == giveaway.winnersCount.value }
}
