package me.y9san9.prizebot.actors.telegram.updater

import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import dev.inmo.tgbotapi.types.InlineMessageId
import kotlinx.coroutines.*
import me.y9san9.aqueue.AQueue
import me.y9san9.prizebot.database.giveaways_active_messages_storage.GiveawaysActiveMessagesStorage
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.user_titles_storage.UserTitlesStorage
import me.y9san9.prizebot.resources.content.giveawayContent
import me.y9san9.telegram.updates.hierarchies.DIBotUpdate


object GiveawayActiveMessagesUpdater {
    private val scope = CoroutineScope(context = Dispatchers.IO)

    private const val UPDATE_RATE = 10_000

    private val queue = AQueue()

    fun <TDI> update (
        update: DIBotUpdate<TDI>,
        giveawayId: Long
    ): Job where TDI : GiveawaysActiveMessagesStorage,
                 TDI : GiveawaysStorage, TDI : UserTitlesStorage = scope.launch {

        println("UPDATING $giveawayId")

        update.di
            .getActiveMessages(giveawayId)
            .map { (inlineId, lastUpdateTime) ->
                println("UPDATING $inlineId of $giveawayId")
                updateMessage(update, giveawayId, inlineId.string, lastUpdateTime)
            }
    }

    private suspend fun <TDI> updateMessage(
        update: DIBotUpdate<TDI>,
        giveawayId: Long,
        inlineId: String,
        lastUpdateTime: Long
    ) where TDI : GiveawaysActiveMessagesStorage,
                 TDI : GiveawaysStorage, TDI : UserTitlesStorage {

        println("BEFORE TIME CHECK: ${lastUpdateTime > System.currentTimeMillis()} $lastUpdateTime ${System.currentTimeMillis()}. $giveawayId")

        if (lastUpdateTime > System.currentTimeMillis()) return

        println("AFTER TIME CHECK, BUT BEFORE LAUNCH: $inlineId $giveawayId")

        queue.execute(AQueueKey.ScheduleUpdate(inlineId)) {
            val nextUpdateTime = lastUpdateTime + UPDATE_RATE
            val delta = nextUpdateTime - System.currentTimeMillis()

            println("NEXT UPDATE: $inlineId $nextUpdateTime, DELTA: $delta, $giveawayId")

            if (delta > 0) {
                update.di.setLastUpdated(InlineMessageId(inlineId), nextUpdateTime)
                scope.launch {
                    delay(delta)
                    println("UPDATING GIVEAWAY FROM DELTA $inlineId $giveawayId")
                    updateMessage(
                        update = update,
                        giveawayId = giveawayId,
                        inlineId = inlineId,
                        lastUpdateTime = 0
                    )
                }
                return@execute
            }

            update.di.setLastUpdated(InlineMessageId(inlineId), System.currentTimeMillis())

            queue.execute(AQueueKey.SendMessage(inlineId)) {
                val giveaway = update.di.getGiveawayById(giveawayId) ?: return@execute
                val (entities, markup) = giveawayContent(update.di, giveaway)
                runCatching {
                    update.bot.editMessageText(InlineMessageId(inlineId), entities, replyMarkup = markup)
                }
            }
        }
    }

    private sealed interface AQueueKey {
        data class ScheduleUpdate(val inlineId: String) : AQueueKey
        data class SendMessage(val inlineId: String) : AQueueKey
    }

}
