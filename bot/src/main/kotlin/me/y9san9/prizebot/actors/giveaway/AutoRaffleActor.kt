package me.y9san9.prizebot.actors.giveaway

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.toChatId
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.forEach
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


class AutoRaffleActor(private val raffleActor: RaffleActorV2) : CoroutineScope {

    /**
     * Map of giveaway id to schedule job
     */
    private val scheduled = mutableMapOf<Long, Job>()

    private val scheduledMutex = Mutex()

    suspend fun scheduleAll (
        bot: TelegramBot,
        di: PrizebotDI
    ) {
        println("> AutoRaffleActor: Schedule all start")
        di.getGiveawaysWithRaffleDate()
            .collect { schedule(bot, it, di) }
        println("> AutoRaffleActor: All raffled scheduled")
    }

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
        println("> AutoRaffleActor: Scheduling giveaway ${giveaway.id}")
        val scope = this

        if(giveaway.raffleDate != null && giveaway.id !in scheduled) {
            scheduled[giveaway.id] = scope.launch {
                val delayMillis = giveaway.raffleDate.toInstant().toEpochMilli() - Instant.now().toEpochMilli()
                println("> AutoRaffleActor: giveaway ${giveaway.id} needs ${delayMillis.coerceAtLeast(0)} millis to raffle")
                delay(delayMillis)
                println("> AutoRaffleActor: giveaway ${giveaway.id} is ready to be raffled")

                val isScheduled = scheduledMutex.withLock {
                    if (giveaway.id in scheduled) {
                        scheduled.remove(giveaway.id)
                        true
                    } else {
                        false
                    }
                }
                println("> AutoRaffleActor: check if giveaway ${giveaway.id} is still in scheduled. Status: ${isScheduled}")

                if (isScheduled && di.getGiveawayById(giveaway.id) != null) {
                    // `scope` used intentionally, so when cancelling parent scope, this job
                    // is not being cancelled
                    scope.launch {
                        println("> AutoRaffleActor: invoke RaffleActor")
                        handleRaffleResult(bot, di, giveaway, raffleActor.raffle(bot, di, giveaway))
                    }
                }
            }
        }
        println("> AutoRaffleActor: giveaway ${giveaway.id} scheduled")
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
                bot.sendMessage(giveaway.ownerId.toChatId(), locale.lackOfParticipants(giveaway.title))
            }
        }
    }


    private fun <T> getEvent(bot: TelegramBot, di: T) = object : DIBotUpdate<T> {
        override val bot = bot
        override val di = di
    }

    override val coroutineContext = Dispatchers.IO
}
