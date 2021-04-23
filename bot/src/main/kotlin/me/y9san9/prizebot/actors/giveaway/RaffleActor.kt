package me.y9san9.prizebot.actors.giveaway

import dev.inmo.tgbotapi.bot.TelegramBot
import kotlinx.coroutines.flow.*
import me.y9san9.prizebot.database.giveaways_storage.ActiveGiveaway
import me.y9san9.random.extensions.shuffledRandomOrg


object RaffleActor {
    suspend fun raffle (
        bot: TelegramBot,
        giveaway: ActiveGiveaway
    ): Boolean {
        val winnerIds = chooseWinners (
            bot, giveaway, ConditionsChecker.cacheChatsUsernames(bot, giveaway)
        ) ?: return false
        giveaway.finish(winnerIds)
        return true
    }

    private suspend fun chooseWinners (
        bot: TelegramBot,
        giveaway: ActiveGiveaway,
        cachedChatsUsernames: Map<Long, String>
    ): List<Long>? {
        return giveaway.participants
            .shuffledRandomOrg()
            .asFlow()
            .map { userId -> userId to ConditionsChecker.check(bot, userId, giveaway, cachedChatsUsernames) }
            .takeWhile { (_, check) -> check !is CheckConditionsResult.GiveawayInvalid }
            .filter { (_, check) -> check is CheckConditionsResult.Success }
            .map { (userId, _) -> userId }
            .take(giveaway.winnersCount.value)
            .toList()
            .takeIf { it.size == giveaway.winnersCount.value }
    }
}
