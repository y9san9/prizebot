package me.y9san9.prizebot.actors.giveaway

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.ChatId
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import me.y9san9.prizebot.actors.storage.giveaways_active_messages_storage.GiveawaysActiveMessagesStorage
import me.y9san9.prizebot.actors.storage.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.actors.storage.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.actors.storage.participants_storage.ParticipantsStorage
import me.y9san9.prizebot.actors.telegram.updater.GiveawayActiveMessagesUpdater
import me.y9san9.prizebot.resources.locales.Locale
import me.y9san9.telegram.updates.hierarchies.DIBotUpdate
import java.time.Instant


object AutoRaffleActor : CoroutineScope {

    /**
     * Map of giveaway id to schedule job
     */
    private val scheduled = mutableMapOf<Long, Job>()

    private val scheduledMutex = Mutex()

    suspend fun <T> scheduleAll (
        bot: TelegramBot,
        di: T
    ) where T : ParticipantsStorage, T : GiveawaysActiveMessagesStorage,
            T : GiveawaysStorage, T : LanguageCodesStorage =
        di.getAllGiveaways()
            .filterIsInstance<ActiveGiveaway>()
            .forEach { schedule(bot, it, di) }

    suspend fun <T> schedule (
        bot: TelegramBot,
        giveaway: ActiveGiveaway,
        di: T
    ) where T : GiveawaysActiveMessagesStorage, T : ParticipantsStorage,
            T : GiveawaysStorage, T : LanguageCodesStorage = scheduledMutex.withLock {
        val scope = this

        if(giveaway.raffleDate != null && giveaway.id !in scheduled) {
            scheduled[giveaway.id] = scope.launch {
                val delay = giveaway.raffleDate.toInstant().toEpochMilli() - Instant.now().toEpochMilli()
                delay(delay.takeIf { it > 0 } ?: 0)

                scheduledMutex.withLock {
                    if (giveaway.id in scheduled && di.getGiveawayById(giveaway.id) != null) {
                        scheduled.remove(giveaway.id)
                        handleRaffleResult(bot, di, giveaway, RaffleActor.raffle(giveaway.id, di))
                    }
                }
            }
        }
    }

    private suspend fun <T> handleRaffleResult (
        bot: TelegramBot, di: T, giveaway: Giveaway, successRaffle: Boolean
    ) where T : GiveawaysActiveMessagesStorage, T : ParticipantsStorage,
            T : GiveawaysStorage, T : LanguageCodesStorage {
        if (successRaffle) {
            GiveawayActiveMessagesUpdater.update(getEvent(bot, di), giveaway.id)
        } else {
            di.removeRaffleDate(giveaway.id)
            val locale = Locale.with(di.getLanguageCode(giveaway.ownerId))
            bot.sendMessage(ChatId(giveaway.ownerId), locale.cannotRaffleGiveaway(giveaway.title))
        }
    }


    private fun <T> getEvent(bot: TelegramBot, di: T) = object : DIBotUpdate<T> {
        override val bot = bot
        override val di = di
    }

    override val coroutineContext = GlobalScope.coroutineContext + Job()
}
