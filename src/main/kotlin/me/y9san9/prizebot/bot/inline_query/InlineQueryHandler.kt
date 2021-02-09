package me.y9san9.prizebot.bot.inline_query

import me.y9san9.prizebot.bot.inline_query.command.PickGiveawayCommand
import me.y9san9.prizebot.bot.inline_query.command.SendGiveawayCommand
import me.y9san9.prizebot.bot.shortcuts.PrizebotInlineQueryUpdate
import me.y9san9.prizebot.resources.INLINE_ACTION_SEND_GIVEAWAY
import me.y9san9.telegram.updates.inline_query.command


suspend fun handleInlineQuery(query: PrizebotInlineQueryUpdate) = query.command {
    case("$INLINE_ACTION_SEND_GIVEAWAY", argsCount = 1) { command ->
        SendGiveawayCommand.handle(query, command)
    }
    raw("") {
        PickGiveawayCommand.handle(query)
    }
    default {
        query.answer()
    }
}
