package me.y9san9.prizebot.handlers.callback_queries.command

import me.y9san9.prizebot.actors.telegram.updater.GiveawayCallbackQueryMessageUpdater
import me.y9san9.prizebot.models.telegram.PrizebotCallbackQueryUpdate


object UpdateCounterCommand {
    suspend fun handle(update: PrizebotCallbackQueryUpdate) {
        update.answer()
        GiveawayCallbackQueryMessageUpdater.update(update, demo = true)
    }
}
