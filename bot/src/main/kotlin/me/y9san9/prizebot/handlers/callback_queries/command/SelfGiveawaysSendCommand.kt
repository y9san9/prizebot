package me.y9san9.prizebot.handlers.callback_queries.command

import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.actors.telegram.updater.GiveawayCallbackQueryMessageUpdater
import me.y9san9.prizebot.models.telegram.PrizebotCallbackQueryUpdate


object SelfGiveawaysSendCommand {
    suspend fun handle(update: PrizebotCallbackQueryUpdate) {
        val giveaway = GiveawayFromCommandExtractor.extract(update, splitter = "_")
            ?.takeIf { it.ownerId == update.chatId } ?: return

        GiveawayCallbackQueryMessageUpdater.update(update, giveaway, demo = true)
    }
}
