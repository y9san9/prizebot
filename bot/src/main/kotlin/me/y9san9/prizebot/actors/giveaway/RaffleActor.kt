package me.y9san9.prizebot.actors.giveaway

import dev.inmo.tgbotapi.bot.TelegramBot
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import me.y9san9.prizebot.database.giveaways_storage.ActiveGiveaway
import me.y9san9.random.extensions.shuffledRandomOrg


object RaffleActor {
    suspend fun raffle (
        bot: TelegramBot,
        giveaway: ActiveGiveaway
    ): Boolean {
        val winnerIds = chooseWinners(bot, giveaway) ?: return false
        giveaway.finish(winnerIds)
        return true
    }

    private suspend fun chooseWinners (
        bot: TelegramBot,
        giveaway: ActiveGiveaway,
    ) = giveaway.participants
        .shuffledRandomOrg()
        .asFlow()
        .filter { userId -> ConditionsChecker.check(bot, userId, giveaway) is CheckConditionsResult.Success }
        .take(giveaway.winnersCount.value)
        .toList()
        .takeIf { it.size == giveaway.winnersCount.value }
}
