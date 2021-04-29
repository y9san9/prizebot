package me.y9san9.prizebot.handlers.callback_queries.command

import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import kotlinx.coroutines.*
import me.y9san9.prizebot.actors.giveaway.RaffleActor
import me.y9san9.prizebot.database.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.database.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.actors.telegram.updater.GiveawayActiveMessagesUpdater
import me.y9san9.prizebot.actors.telegram.updater.GiveawayCallbackQueryMessageUpdater
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.extensions.telegram.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.resources.markups.raffleProcessingMarkup
import me.y9san9.telegram.extensions.asTextContentMessage


object RaffleCommand : CoroutineScope {

    fun handle(update: PrizebotCallbackQueryUpdate) {
        val giveaway = GiveawayFromCommandExtractor.extract(update, splitter = "_") ?: return

        launch {
            if (giveaway is ActiveGiveaway) {
                val job = launchRaffleProcessingEdit(update)

                val success = RaffleActor
                    .raffle(update.bot, giveaway, update.di)

                job.join()
                updateMessage(update, update.di.getGiveawayById(giveaway.id)!!)

                if (!success)
                    return@launch update.answer(text = update.locale.participantsCountIsNotEnough)

            } else updateMessage(update, giveaway)

            GiveawayActiveMessagesUpdater.update(update, giveaway.id)

            update.answer()
        }
    }

    private fun launchRaffleProcessingEdit(update: PrizebotCallbackQueryUpdate) = launch {
        update.bot.editMessageText (
            message = update.message?.asTextContentMessage() ?: return@launch,
            text = update.locale.raffleProcessing,
            replyMarkup = raffleProcessingMarkup()
        )
    }

    private suspend fun updateMessage(update: PrizebotCallbackQueryUpdate, giveaway: Giveaway) =
        GiveawayCallbackQueryMessageUpdater.update(update, giveaway, demo = true)

    override val coroutineContext = GlobalScope.coroutineContext + Job()
}
