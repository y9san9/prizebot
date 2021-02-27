package me.y9san9.prizebot.handlers.inline_queries

import me.y9san9.prizebot.handlers.inline_queries.command.PickGiveawayCommand
import me.y9san9.prizebot.handlers.inline_queries.command.SendGiveawayCommand
import me.y9san9.prizebot.extensions.telegram.PrizebotInlineQueryUpdate
import me.y9san9.prizebot.resources.INLINE_ACTION_SEND_GIVEAWAY
import me.y9san9.telegram.updates.extensions.command.commandOrAnswer


object InlineQueryHandler {
    suspend fun handle(update: PrizebotInlineQueryUpdate) = update.commandOrAnswer(splitter = "_") {
        case("$INLINE_ACTION_SEND_GIVEAWAY", argsCount = 1) {
            SendGiveawayCommand.handle(update)
        }
        raw("") {
            PickGiveawayCommand.handle(update)
        }
    }
}
