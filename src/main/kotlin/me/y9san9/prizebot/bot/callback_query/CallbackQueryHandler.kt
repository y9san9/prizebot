package me.y9san9.prizebot.bot.callback_query

import dev.inmo.tgbotapi.extensions.api.chat.members.getChatMember
import dev.inmo.tgbotapi.types.ChatId
import me.y9san9.prizebot.bot.callback_query.command.*
import me.y9san9.telegram.updates.callback_query.command
import me.y9san9.prizebot.bot.shortcuts.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.resources.CALLBACK_ACTION_DELETE_GIVEAWAY
import me.y9san9.prizebot.resources.CALLBACK_ACTION_PARTICIPATE
import me.y9san9.prizebot.resources.CALLBACK_ACTION_RAFFLE_GIVEAWAY
import me.y9san9.prizebot.resources.CALLBACK_ACTION_SELF_GIVEAWAYS_CONTROL


suspend fun handleCallbackQuery(query: PrizebotCallbackQueryUpdate) = query.command {
    case("$CALLBACK_ACTION_PARTICIPATE", argsCount = 1) { command ->
        ParticipationCommand.handle(query, command)
    }
    case("$CALLBACK_ACTION_SELF_GIVEAWAYS_CONTROL", argsCount = 1) { command ->
        SelfGiveawaysSendCommand.handle(query, command)
    }
    case("$CALLBACK_ACTION_SELF_GIVEAWAYS_CONTROL", argsCount = 2) { command ->
        SelfGiveawaysButtonsCommand.handle(query, command)
    }
    case("$CALLBACK_ACTION_DELETE_GIVEAWAY", argsCount = 1) { command ->
        DeleteGiveawayCommand.handle(query, command)
    }
    case("$CALLBACK_ACTION_RAFFLE_GIVEAWAY", argsCount = 1) { command ->
        RaffleCommand.handle(query, command)
    }
    default {
        query.answer()
    }
}
