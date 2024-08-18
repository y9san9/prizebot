@file:Suppress("OPT_IN_USAGE")

package me.y9san9.prizebot.actors.giveaway

import dev.inmo.tgbotapi.bot.TelegramBot
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import me.y9san9.aqueue.flow.launchInAQueue
import me.y9san9.prizebot.conditions.BaseConditionsClient
import me.y9san9.prizebot.conditions.BaseConditionsClient.*
import me.y9san9.prizebot.database.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.di.PrizebotDI
import me.y9san9.random.RandomOrgClient
import me.y9san9.telegram.extensions.telegram_bot.getUserTitleOrNull
import kotlin.Result


class RaffleActor(randomOrgApiKey: String) {
    private val random = RandomOrgClient(randomOrgApiKey)

    private val scope: CoroutineScope = GlobalScope

    private val requests = MutableSharedFlow<Triple<TelegramBot, PrizebotDI, ActiveGiveaway>>(replay = 1)
    private val responses = MutableSharedFlow<Result<Pair<ActiveGiveaway, Boolean>>>()

    init {
        requests
            .launchInAQueue(
                scope,
                key = { (_, _, giveaway) -> giveaway.id },
                action = { (bot, di, giveaway) ->
                    responses.emit (
                        runCatching {
                            giveaway to raffleAction(bot, di, giveaway)
                        }
                    )
                }
            )
    }

    private suspend fun raffleAction (
        bot: TelegramBot,
        di: PrizebotDI,
        giveaway: ActiveGiveaway
    ): Boolean {
        val winnerIds = chooseWinners(di.conditionsClient, giveaway) ?: return false

        giveaway.finish(winnerIds)
        winnerIds.forEach { id ->
            bot.getUserTitleOrNull(id)?.let { di.saveUserTitle(id, it) }
        }

        return true
    }

    /**
     * Different giveaways raffles should be parallelled while one giveaway raffles should be consistent to prevent
     * double raffle
     */
    suspend fun raffle (
        bot: TelegramBot,
        giveaway: ActiveGiveaway,
        di: PrizebotDI
    ): Boolean {
        scope.launch { requests.emit(Triple(bot, di, giveaway)) }

        return responses.map { it.getOrThrow() }.first { (g) -> g.id == giveaway.id }.second
    }

    private suspend fun chooseWinners(
        checker: BaseConditionsClient,
        giveaway: ActiveGiveaway
    ): List<Long>? {
        return random.shuffled(giveaway.participants)
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
}
