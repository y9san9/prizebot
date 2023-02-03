package me.y9san9.prizebot.actors.telegram.updater

import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import dev.inmo.tgbotapi.types.InlineMessageIdentifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import me.y9san9.extensions.job.JobLauncher
import me.y9san9.prizebot.database.giveaways_active_messages_storage.GiveawaysActiveMessagesStorage
import me.y9san9.prizebot.database.giveaways_storage.Giveaway
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.user_titles_storage.UserTitlesStorage
import me.y9san9.prizebot.resources.content.giveawayContent
import me.y9san9.telegram.updates.hierarchies.DIBotUpdate


object GiveawayActiveMessagesUpdater {
    private val scope = CoroutineScope(context = SupervisorJob())

    private const val UPDATE_RATE = 10_000

    private val jobLauncher = JobLauncher(scope)

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
                updateMessage(update, giveawayId, inlineId, lastUpdateTime)
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

        jobLauncher.launch(JobId.ScheduleUpdate(inlineId)) {
            val nextUpdateTime = lastUpdateTime + UPDATE_RATE
            val delta = nextUpdateTime - System.currentTimeMillis()

            println("NEXT UPDATE: $inlineId $nextUpdateTime, DELTA: $delta, $giveawayId")

            if (delta > 0) {
                update.di.setLastUpdated(inlineId, nextUpdateTime)
                scope.launch delayed@{
                    delay(delta)
                    println("UPDATING GIVEAWAY FROM DELTA $inlineId $giveawayId")
                    updateMessage(
                        update = update,
                        giveawayId = giveawayId,
                        inlineId = inlineId,
                        lastUpdateTime = 0
                    )
                }
                return@launch
            }

            update.di.setLastUpdated(inlineId, System.currentTimeMillis())

            jobLauncher.launch(JobId.SendMessage(inlineId)) {
                val giveaway = update.di.getGiveawayById(giveawayId) ?: return@launch
                val (entities, markup) = giveawayContent(update.di, giveaway)
                runCatching {
                    update.bot.editMessageText(inlineId, entities, replyMarkup = markup)
                }
            }
        }
    }

    private sealed interface JobId {
        data class ScheduleUpdate(val inlineId: String) : JobId
        data class SendMessage(val inlineId: String) : JobId
    }

}
