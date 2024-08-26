package me.y9san9.prizebot.actors.telegram.updater

import dev.inmo.micro_utils.coroutines.safelyWithoutExceptions
import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import me.y9san9.prizebot.database.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.extensions.telegram.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.resources.content.giveawayContent
import me.y9san9.telegram.extensions.asTextContentMessage


object GiveawayCallbackQueryMessageUpdater {

    private val coroutineScope = CoroutineScope(Dispatchers.IO) + SupervisorJob()
    private val giveawayEdits = Channel<GiveawayEdit>()

    init {
        coroutineScope.launch {
            val pendingEdits = mutableListOf<GiveawayEdit>()

            for (edit in giveawayEdits) {
                // cancel all the previous edits
                pendingEdits
                    .filter { pendingEdit -> pendingEdit isSame edit }
                    .forEach { pendingEdit -> pendingEdit.job.cancelAndJoin() }

                edit.job.invokeOnCompletion {
                    pendingEdits.removeAll { pendingEdit -> pendingEdit.job === edit.job }
                }

                pendingEdits += edit
            }
        }
    }

    suspend fun update(
        update: PrizebotCallbackQueryUpdate,
        demo: Boolean = false,
        splitter: String = "_"
    ) {
        val giveaway = GiveawayFromCommandExtractor.extract(update, splitter) ?: return
        update(update, giveaway, demo)
    }

    suspend fun update(
        update: PrizebotCallbackQueryUpdate,
        giveaway: Giveaway,
        demo: Boolean = false
    ) {
        val inlineMessageId = update.inlineMessageId
        val message = update.message?.asTextContentMessage()

        val (entities, markup) = giveawayContent(update.di, giveaway, demo)

        val edit = when {
            inlineMessageId != null -> GiveawayEdit.Inline(
                inlineMessageId = inlineMessageId.string,
                job = coroutineScope.launch {
                    safelyWithoutExceptions {
                        update.bot.editMessageText(inlineMessageId, entities, replyMarkup = markup)
                    }
                }
            )
            message != null -> GiveawayEdit.Message(
                messageId = message.messageId.long,
                job = coroutineScope.launch {
                    safelyWithoutExceptions {
                        update.bot.editMessageText(message, entities, replyMarkup = markup)
                    }
                }
            )
            else -> return
        }

        coroutineScope.launch {
            giveawayEdits.send(edit)
        }
    }

    sealed class GiveawayEdit(val job: Job) {
        abstract infix fun isSame(other: GiveawayEdit): Boolean

        class Message(private val messageId: Long, job: Job) : GiveawayEdit(job) {
            override fun isSame(other: GiveawayEdit) =
                other is Message && messageId == other.messageId
        }
        class Inline(private val inlineMessageId: String, job: Job) : GiveawayEdit(job) {
            override fun isSame(other: GiveawayEdit) =
                other is Inline && inlineMessageId == other.inlineMessageId
        }
    }
}
