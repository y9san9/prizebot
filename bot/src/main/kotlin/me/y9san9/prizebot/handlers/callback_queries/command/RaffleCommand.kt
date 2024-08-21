package me.y9san9.prizebot.handlers.callback_queries.command

import dev.inmo.micro_utils.coroutines.launchSafelyWithoutExceptions
import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.ChatId
import kotlinx.coroutines.*
import me.y9san9.prizebot.actors.giveaway.RaffleActor
import me.y9san9.prizebot.database.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.database.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.actors.telegram.updater.GiveawayActiveMessagesUpdater
import me.y9san9.prizebot.actors.telegram.updater.GiveawayCallbackQueryMessageUpdater
import me.y9san9.prizebot.extensions.telegram.getLocale
import me.y9san9.prizebot.extensions.telegram.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.resources.markups.raffleProcessingMarkup
import me.y9san9.telegram.extensions.asTextContentMessage


object RaffleCommand : CoroutineScope {

    fun handle(update: PrizebotCallbackQueryUpdate) {
        launch {
            val giveaway = GiveawayFromCommandExtractor.extract(update, splitter = "_") ?: return@launch
            println("> RaffleCommand: [${giveaway.id}] Raffle command received for giveaway")

            if (giveaway is ActiveGiveaway) {
                println("> RaffleCommand: [${giveaway.id}] Launch 'Processing...' message edit")
                val job = launchRaffleProcessingEdit(update, giveaway.id)
                println("> RaffleCommand: [${giveaway.id}] Before raffle")
                val success = update.di.raffleActor.raffle(update.bot, update.di, giveaway)
                println("> RaffleCommand: [${giveaway.id}] After raffle")

                job.join()

                println("> RaffleCommand: [${giveaway.id}] Updating giveaway message")
                updateMessage(update, update.di.getGiveawayById(giveaway.id)!!)
                println("> RaffleCommand: [${giveaway.id}] Updated giveaway message")

                if (!success) {
                    update.answer(text = update.getLocale().participantsCountIsNotEnough)
                    println("> RaffleCommand: [${giveaway.id}] Answered on click that there is not enough participants")
                    return@launch
                }

            } else updateMessage(update, giveaway)

            GiveawayActiveMessagesUpdater.update(update, giveaway.id)

            update.answer()
        }
    }

    private fun launchRaffleProcessingEdit(
        update: PrizebotCallbackQueryUpdate,
        giveawayId: Long
    ) = launch {
        runCatching {
            update.bot.editMessageText(
                message = update.message?.asTextContentMessage() ?: return@launch,
                text = update.getLocale().raffleProcessing,
                replyMarkup = raffleProcessingMarkup()
            )
        }.onFailure {
            println("> RaffleCommand: [$giveawayId] Cannot update message. ${it.message}")
        }
        println("> RaffleCommand: [$giveawayId] 'Processing...' message edited")
    }

    private suspend fun updateMessage(update: PrizebotCallbackQueryUpdate, giveaway: Giveaway) =
        GiveawayCallbackQueryMessageUpdater.update(update, giveaway, demo = true)

    override val coroutineContext = Dispatchers.IO
}
