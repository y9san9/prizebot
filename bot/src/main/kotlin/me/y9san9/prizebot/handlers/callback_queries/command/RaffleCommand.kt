package me.y9san9.prizebot.handlers.callback_queries.command

import me.y9san9.prizebot.actors.raffle.RaffleActor
import me.y9san9.prizebot.actors.storage.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.actors.telegram.updater.GiveawayCallbackQueryMessageUpdater
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.models.telegram.PrizebotCallbackQueryUpdate


object RaffleCommand {

    @Suppress("EXPERIMENTAL_API_USAGE")
    suspend fun handle(update: PrizebotCallbackQueryUpdate) {
        val giveaway = GiveawayFromCommandExtractor.extract(update, splitter = "_") ?: return

        if(giveaway is ActiveGiveaway) {
            val winnerId = RaffleActor
                .raffle(giveaway.id, update.di)

            if (winnerId == null) {
                update.answer(text = update.locale.nobodyIsParticipatingYet)
                return
            } else {
                update.di.finishGiveaway(giveaway.id, winnerId)
                updateMessage(update, update.di.getGiveawayById(giveaway.id)!!)
            }
        } else updateMessage(update, giveaway)

        update.answer()
    }

    private suspend fun updateMessage(update: PrizebotCallbackQueryUpdate, giveaway: Giveaway) =
        GiveawayCallbackQueryMessageUpdater.update(update, giveaway, demo = true)
}
