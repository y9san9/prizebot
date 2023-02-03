package me.y9san9.prizebot.handlers.callback_queries

import me.y9san9.prizebot.extensions.telegram.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.handlers.callback_queries.command.ConfirmCommand
import me.y9san9.prizebot.handlers.callback_queries.command.DeleteGiveawayCommand
import me.y9san9.prizebot.handlers.callback_queries.command.ParticipateCommand
import me.y9san9.prizebot.handlers.callback_queries.command.RaffleCommand
import me.y9san9.prizebot.handlers.callback_queries.command.SelectLocaleCommand
import me.y9san9.prizebot.handlers.callback_queries.command.SelfGiveawaysButtonsCommand
import me.y9san9.prizebot.handlers.callback_queries.command.SelfGiveawaysSendCommand
import me.y9san9.prizebot.handlers.callback_queries.command.UpdateCounterCommand
import me.y9san9.prizebot.handlers.callback_queries.command.UpdateDemoCounterCommand
import me.y9san9.prizebot.resources.CALLBACK_ACTION_CONFIRM
import me.y9san9.prizebot.resources.CALLBACK_ACTION_DELETE_GIVEAWAY
import me.y9san9.prizebot.resources.CALLBACK_ACTION_PARTICIPATE
import me.y9san9.prizebot.resources.CALLBACK_ACTION_RAFFLE_GIVEAWAY
import me.y9san9.prizebot.resources.CALLBACK_ACTION_SELECT_LOCALE
import me.y9san9.prizebot.resources.CALLBACK_ACTION_SELF_GIVEAWAYS_CONTROL
import me.y9san9.prizebot.resources.CALLBACK_ACTION_UPDATE_COUNTER
import me.y9san9.prizebot.resources.CALLBACK_ACTION_UPDATE_DEMO_COUNTER
import me.y9san9.prizebot.resources.CALLBACK_NO_ACTION
import me.y9san9.telegram.updates.extensions.command.commandOrAnswer


object CallbackQueryHandler {

    suspend fun handle(update: PrizebotCallbackQueryUpdate) = update.commandOrAnswer(splitter = "_") {
        case("$CALLBACK_NO_ACTION") {
            update.answer()
        }
        case("$CALLBACK_ACTION_PARTICIPATE", argsCount = 1) {
            ParticipateCommand.handle(update)
        }
        case("$CALLBACK_ACTION_SELF_GIVEAWAYS_CONTROL", argsCount = 1) {
            SelfGiveawaysSendCommand.handle(update)
        }
        case("$CALLBACK_ACTION_CONFIRM", argsCount = 2) {
            ConfirmCommand.handle(update)
        }
        case("$CALLBACK_ACTION_SELF_GIVEAWAYS_CONTROL", argsCount = 2) {
            SelfGiveawaysButtonsCommand.handle(update)
        }
        case("$CALLBACK_ACTION_DELETE_GIVEAWAY", argsCount = 1) {
            DeleteGiveawayCommand.handle(update)
        }
        case("$CALLBACK_ACTION_RAFFLE_GIVEAWAY", argsCount = 1) {
            RaffleCommand.handle(update)
        }
        case("$CALLBACK_ACTION_UPDATE_COUNTER", argsCount = 1) {
            UpdateCounterCommand.handle(update)
        }
        case("$CALLBACK_ACTION_UPDATE_DEMO_COUNTER", argsCount = 1) {
            UpdateDemoCounterCommand.handle(update)
        }
        case("$CALLBACK_ACTION_SELECT_LOCALE", argsCount = 1) {
            SelectLocaleCommand.handle(update)
        }
    }.also {
        println("ANSWERED! ${update.query}")
    }
}
