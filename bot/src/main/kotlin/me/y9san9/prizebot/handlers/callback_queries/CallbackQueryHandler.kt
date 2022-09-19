package me.y9san9.prizebot.handlers.callback_queries

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import me.y9san9.prizebot.handlers.callback_queries.command.*
import me.y9san9.prizebot.extensions.telegram.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.resources.*
import me.y9san9.telegram.updates.extensions.command.commandOrAnswer


object CallbackQueryHandler {

    private val participateMutex = Mutex()

    suspend fun handle(update: PrizebotCallbackQueryUpdate) = update.commandOrAnswer(splitter = "_") {
        case("$CALLBACK_NO_ACTION") {
            update.answer()
        }
        case("$CALLBACK_ACTION_PARTICIPATE", argsCount = 1) {
            participateMutex.withLock {
                ParticipateCommand.handle(update)
            }
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
    }
}
