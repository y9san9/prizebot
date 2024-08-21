package me.y9san9.prizebot.actors.giveaway

import dev.inmo.tgbotapi.bot.TelegramBot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import me.y9san9.aqueue.AQueue
import me.y9san9.aqueue.io
import me.y9san9.prizebot.conditions.BaseConditionsClient
import me.y9san9.prizebot.conditions.BaseConditionsClient.Condition
import me.y9san9.prizebot.database.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.di.PrizebotDI
import me.y9san9.random.RandomOrgClient
import me.y9san9.telegram.extensions.telegram_bot.getUserTitleOrNull

class RaffleActorV2(
    randomOrgApiKey: String,
    scope: CoroutineScope,
    private val queue: AQueue = AQueue.io()
) {
    private val random = RandomOrgClient(randomOrgApiKey, scope)

    suspend fun raffle(
        bot: TelegramBot,
        di: PrizebotDI,
        giveaway: ActiveGiveaway
    ): Boolean {
        println("> RaffleActorV2: Received request to raffle giveaway ${giveaway.id}")
        val result = queue.execute(key = giveaway.id) { raffleAction(bot, di, giveaway) }
        println("> RaffleActorV2: Giveaway ${giveaway.id} raffled")
        return result
    }

    private suspend fun raffleAction(
        bot: TelegramBot,
        di: PrizebotDI,
        giveaway: ActiveGiveaway
    ): Boolean {
        println("> RaffleActorV2: Received raffle execution for giveaway ${giveaway.id}")

        val winnerIds = chooseWinners(di.conditionsClient, giveaway) ?: run {
            println("> RaffleActorV2: Can't raffle giveaway ${giveaway.id} for some reason")
            return false
        }

        println("> RaffleActorV2: Winners are chosen for giveaway ${giveaway.id}")

        giveaway.finish(winnerIds)

        println("> RaffleActorV2: Giveaway ${giveaway.id} is marked as finished")

        winnerIds.forEach { id ->
            bot.getUserTitleOrNull(id)?.let { di.saveUserTitle(id, it) }
        }

        println("> RaffleActorV2: Winner titles from giveaway ${giveaway.id} successfully saved")

        return true
    }

    // todo: rewrite conditions checker if it won't stop crashing
    private suspend fun chooseWinners(
        checker: BaseConditionsClient,
        giveaway: ActiveGiveaway
    ): List<Long>? {
        return random.shuffled(giveaway.getParticipantsIds())
            .asFlow()
            // Custom discriminator, so it won't be mixed with regular participate action
            .map { userId -> userId to checker.check(userId, giveaway, discriminator = -userId).await().condition }
            .takeWhile { (_, check) -> check !is Condition.PermanentUsername }
            .filter { (_, check) -> check == null }
            .map { (userId, _) -> userId }
            .take(giveaway.winnersCount.value)
            .toList()
            .takeIf { it.size == giveaway.winnersCount.value }
    }

    private class Response(
        val status: Boolean
    )

    class Request(
        val giveawayId: Int,
    )
}
