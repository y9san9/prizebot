package me.y9san9.prizebot.handlers.private_messages.fsm

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import me.y9san9.fsm.FSM
import me.y9san9.fsm.FSMStates
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMStorage
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate

fun FSM.Companion.prizebotPrivateMessages (
    events: Flow<PrizebotPrivateMessageUpdate>,
    states: FSMStates<PrizebotPrivateMessageUpdate>,
    storage: PrizebotFSMStorage,
    scope: CoroutineScope
) = FSM(events, states, storage, scope) { it.chatId }
