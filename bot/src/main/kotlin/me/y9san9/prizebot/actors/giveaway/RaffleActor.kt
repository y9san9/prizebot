package me.y9san9.prizebot.actors.giveaway

import me.y9san9.prizebot.database.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.random.extensions.shuffledRandomOrg


object RaffleActor {
    suspend fun raffle (
        giveaway: ActiveGiveaway
    ): Boolean {
        val winnerIds = chooseWinners(giveaway) ?: return false
        giveaway.finish(winnerIds)
        return true
    }

    private suspend fun chooseWinners (
        giveaway: ActiveGiveaway,
    ) = giveaway.participants
        .shuffledRandomOrg()
        .take(giveaway.winnersCount.value)
        .takeIf { it.size == giveaway.winnersCount.value }
}
