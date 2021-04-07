package me.y9san9.prizebot.handlers.callback_queries.command

import me.y9san9.prizebot.actors.giveaway.RaffleActor
import me.y9san9.prizebot.actors.storage.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.actors.telegram.updater.GiveawayActiveMessagesUpdater
import me.y9san9.prizebot.actors.telegram.updater.GiveawayCallbackQueryMessageUpdater
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.extensions.telegram.PrizebotCallbackQueryUpdate


object RaffleCommand {

    @Suppress("EXPERIMENTAL_API_USAGE")
    suspend fun handle(update: PrizebotCallbackQueryUpdate) {
        val giveaway = GiveawayFromCommandExtractor.extract(update, splitter = "_") ?: return

        if(giveaway is ActiveGiveaway) {
            val success = RaffleActor
                .raffle(giveaway.id, update.di)

            if (success)
                updateMessage(update, update.di.getGiveawayById(giveaway.id)!!)
            else
                return update.answer(text = update.locale.nobodyIsParticipatingYet)

        } else updateMessage(update, giveaway)

        GiveawayActiveMessagesUpdater.update(update, giveaway.id)

        update.answer()
    }

    private suspend fun updateMessage(update: PrizebotCallbackQueryUpdate, giveaway: Giveaway) =
        GiveawayCallbackQueryMessageUpdater.update(update, giveaway, demo = true)
}
