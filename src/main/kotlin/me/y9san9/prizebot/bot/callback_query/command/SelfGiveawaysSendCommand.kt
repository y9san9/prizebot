package me.y9san9.prizebot.bot.callback_query.command

import me.y9san9.prizebot.bot.shortcuts.*
import me.y9san9.prizebot.logic.actor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.logic.actor.GiveawayMessageUpdater
import me.y9san9.telegram.updates.message.Command


object SelfGiveawaysSendCommand {
    suspend fun handle(query: PrizebotCallbackQueryUpdate, command: Command) {
        val giveaway = GiveawayFromCommandExtractor.extract(query.di, command)
            ?.takeIf { it.ownerId == query.from.id.chatId } ?: return

        GiveawayMessageUpdater.update(query, giveaway, demo = true)
    }
}
