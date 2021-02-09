package me.y9san9.prizebot.bot.shortcuts

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import me.y9san9.fsm.FSM
import me.y9san9.fsm.storage.MemoryStorage
import me.y9san9.telegram.updates.callback_query.BotCallbackQueryUpdate
import me.y9san9.telegram.updates.inline_query.BotInlineQueryUpdate
import me.y9san9.telegram.updates.message.BotMessageUpdate
import me.y9san9.prizebot.bot.di.PrizebotDI
import me.y9san9.prizebot.resources.locales.Locale
import me.y9san9.telegram.updates.*


typealias PrizebotMessageUpdate = BotMessageUpdate<PrizebotDI>
typealias PrizebotInlineQueryUpdate = BotInlineQueryUpdate<PrizebotDI>
typealias PrizebotCallbackQueryUpdate = BotCallbackQueryUpdate<PrizebotDI>

typealias FSMPrizebotStates = FSMTelegramStates<PrizebotDI>
typealias FSMPrizebotState<TDataIn> = FSMTelegramState<TDataIn, PrizebotDI>
typealias FSMInitialPrizebotState = FSMPrizebotState<Unit>
typealias PrizebotFSM = TelegramFSM<PrizebotDI>


fun FSM.Companion.prizebot (
    events: Flow<PrizebotMessageUpdate>,
    states: FSMPrizebotStates,
    storage: FSMTelegramStorage = MemoryStorage(),
    scope: CoroutineScope
): PrizebotFSM = telegram(events, states, storage, scope)


val PrizebotCallbackQueryUpdate.locale get() = Locale.with(languageCode)
val PrizebotInlineQueryUpdate.locale get() = Locale.with(languageCode)
val PrizebotMessageUpdate.locale get() = Locale.with(languageCode)
