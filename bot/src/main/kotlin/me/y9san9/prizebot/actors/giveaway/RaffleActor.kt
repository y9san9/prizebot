package me.y9san9.prizebot.actors.giveaway

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.chat.abstracts.PrivateChat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import me.y9san9.extensions.flow.createParallelLauncher
import me.y9san9.prizebot.database.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.database.user_titles_storage.UserTitlesStorage
import me.y9san9.prizebot.di.PrizebotDI
import me.y9san9.random.extensions.shuffledRandomOrg
import me.y9san9.telegram.extensions.telegram_bot.getUserTitle


object RaffleActor {
    private val scope = CoroutineScope(context = GlobalScope.coroutineContext + Job())

    private val requests = MutableSharedFlow<Triple<TelegramBot, PrizebotDI, ActiveGiveaway>>()
    private val responses = MutableSharedFlow<Pair<ActiveGiveaway, Boolean>>()

    init {
        requests
            .createParallelLauncher()
            .launchEach (
                scope,
                mutexKey = { (_, _, giveaway) -> giveaway.id },
                consumer = { (bot, di, giveaway) -> responses.emit(giveaway to raffleAction(bot, di, giveaway)) }
            )
    }

    private suspend fun raffleAction (
        bot: TelegramBot,
        titlesStorage: UserTitlesStorage,
        giveaway: ActiveGiveaway
    ): Boolean {
        val winnerIds = chooseWinners (
            bot,
            giveaway,
            ConditionsChecker.cacheChatsUsernames(bot, giveaway)
        ) ?: return false

        giveaway.finish(winnerIds)
        winnerIds.forEach { id ->
            bot.getUserTitle(id)?.let { titlesStorage.saveUserTitle(id, it) }
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
        requests.emit(Triple(bot, di, giveaway))

        return responses.first { (g) -> g.id == giveaway.id }
            .second
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
