package me.y9san9.telegram.updates

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import me.y9san9.fsm.FSM
import me.y9san9.fsm.FSMState
import me.y9san9.fsm.FSMStates
import me.y9san9.fsm.FSMStorage
import me.y9san9.fsm.storage.MemoryStorage
import me.y9san9.telegram.updates.message.BotMessageUpdate


typealias FSMTelegramStates<T> = FSMStates<BotMessageUpdate<T>>
typealias FSMTelegramState<TDataIn, DI> = FSMState<TDataIn, BotMessageUpdate<DI>>
typealias FSMInitialTelegramState<DI> = FSMTelegramState<Unit, DI>
typealias FSMTelegramStorage = FSMStorage<Long, Any?>
typealias TelegramFSM<DI> = FSM<Long, BotMessageUpdate<DI>>


fun <DI> FSM.Companion.telegram (
    events: Flow<BotMessageUpdate<DI>>,
    states: FSMTelegramStates<DI>,
    storage: FSMTelegramStorage = MemoryStorage(),
    scope: CoroutineScope
): TelegramFSM<DI> = FSM(events, states, storage, scope) { it.chatId }
