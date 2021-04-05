package me.y9san9.fsm

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import me.y9san9.fsm.storage.MemoryStorage

typealias SingleChannelFSM <TEvent> = FSM<Unit, TEvent>

class FSM <TChannel, TEvent> (
    private val events: Flow<TEvent>,
    private val states: FSMStates<TEvent>,
    storage: FSMStorage<TChannel, Any?> = MemoryStorage(),
    private val scope: CoroutineScope = CoroutineScope(context = GlobalScope.coroutineContext + Job()),
    private val throwableHandler: suspend (Throwable) -> Unit = { throw it },
    /**
     *  Used to split FSM states by channels. For example in telegram bot there will be Peer as event splitter.
     *  In android or text quests you should use [singleChannel] constructor.
     */
    private val eventChannelProvider: suspend (TEvent) -> TChannel
) {
    companion object {
        fun <TEvent> singleChannel (
            events: Flow<TEvent>,
            states: FSMStates<TEvent>,
            storage: FSMStorage<Unit, Any?> = MemoryStorage(),
            scope: CoroutineScope = CoroutineScope(context = GlobalScope.coroutineContext + Job()),
            throwableHandler: suspend (Throwable) -> Unit = { throw it },
        ): SingleChannelFSM<TEvent> = FSM (
            events = events,
            states = states,
            storage = storage,
            scope = scope,
            throwableHandler = throwableHandler
        ) { }
    }

    private val contextManager = FSMContextManager (
        initialName = states.initial.name,
        storage = storage
    )

    init {
        scope.launch {
            processEvents(events, states.states)
        }
    }

    private suspend fun processEvents(events: Flow<TEvent>, states: List<FSMState<*, TEvent>>) {
        while(true) {
            val event = events.first()
            val channel = eventChannelProvider(event)
            val context = contextManager.get(channel)

            val processingState = states
                .singleOrNull { it.name == context.name }
                ?: error("Exactly one state with name ${context.name} expected")

            @Suppress("UNCHECKED_CAST")
            suspend fun <TDataIn> uncheckedProcess(processingState: FSMState<TDataIn, TEvent>) {
                // This cast is always successful because of state data out == next state data in
                val result = try {
                    processingState.process(context.data as TDataIn, event)
                } catch (throwable: Throwable) {
                    throwableHandler(throwable)
                    return
                }

                contextManager.set (
                    channel, FSMContext (
                        name = result.nextState.name,
                        data = result.data
                    )
                )
            }

            uncheckedProcess(processingState)
        }
    }
}
