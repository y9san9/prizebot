package me.y9san9.prizebot.actors.giveaway

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.ChatId
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import me.y9san9.prizebot.database.giveaways_active_messages_storage.GiveawaysActiveMessagesStorage
import me.y9san9.prizebot.database.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.actors.telegram.updater.GiveawayActiveMessagesUpdater
import me.y9san9.prizebot.database.user_titles_storage.UserTitlesStorage
import me.y9san9.prizebot.di.PrizebotDI
import me.y9san9.prizebot.resources.locales.Locale
import me.y9san9.telegram.updates.hierarchies.DIBotUpdate
import java.time.Instant


class AutoRaffleActor(private val raffleActor: RaffleActor) : CoroutineScope {

    /**
     * Map of giveaway id to schedule job
     */
    private val scheduled = mutableMapOf<Long, Job>()

    private val scheduledMutex = Mutex()

    suspend fun scheduleAll (
        bot: TelegramBot,
        di: PrizebotDI
    ) = di.getAllGiveaways()
            .filterIsInstance<ActiveGiveaway>()
            .forEach { schedule(bot, it, di) }

    suspend fun cancelSchedulesRaffle(giveawayId: Long): Boolean {
        println("[$giveawayId]: CANCELLING RAFFLE")
        return scheduledMutex.withLock {
            println("[$giveawayId]: GOT MUTEX")
            scheduled[giveawayId]?.cancelAndJoin()
            println("[$giveawayId]: CANCELLED")
            scheduled.remove(giveawayId) != null
        }
    }

    suspend fun schedule (
        bot: TelegramBot,
        giveaway: ActiveGiveaway,
        di: PrizebotDI
    ) = scheduledMutex.withLock {
        val scope = this

        if(giveaway.raffleDate != null && giveaway.id !in scheduled) {
            scheduled[giveaway.id] = scope.launch {
                val delayMillis = giveaway.raffleDate.toInstant().toEpochMilli() - Instant.now().toEpochMilli()
                delay(delayMillis)

                scheduledMutex.lock()
                if (giveaway.id in scheduled && di.getGiveawayById(giveaway.id) != null) {
                    scheduled.remove(giveaway.id)
                    scheduledMutex.unlock()
                    handleRaffleResult(bot, di, giveaway, raffleActor.raffle(bot, giveaway, di))
                }
                if (scheduledMutex.isLocked) scheduledMutex.unlock()
            }
        }
    }

    private suspend fun <T> handleRaffleResult (
        bot: TelegramBot, di: T,
        giveaway: ActiveGiveaway, successRaffle: Boolean
    ) where T : GiveawaysActiveMessagesStorage,
            T : GiveawaysStorage, T : LanguageCodesStorage, T : UserTitlesStorage {
        if (successRaffle) {
            GiveawayActiveMessagesUpdater.update(getEvent(bot, di), giveaway.id)
        } else {
            giveaway.removeRaffleDate()
            val locale = Locale.with(di.getLanguageCode(giveaway.ownerId))
            runCatching {
                bot.sendMessage(ChatId(giveaway.ownerId), locale.lackOfParticipants(giveaway.title))
            }
        }
    }


    private fun <T> getEvent(bot: TelegramBot, di: T) = object : DIBotUpdate<T> {
        override val bot = bot
        override val di = di
    }

    override val coroutineContext = GlobalScope.coroutineContext + Job()
}
