# Finite State Machine
> Module with implementation common state machine for any kind of usage (it may be separated from the project and used for Android, Text Quests, etc)

## Structure

There are some entities you should know:
- [FSMStorage](src/main/kotlin/me/y9san9/fsm/FSMStorage.kt) - an interface used for loading/saving state which may be defined as you want. The default implementation is [MemoryStorage](src/main/kotlin/me/y9san9/fsm/storage/MemoryStorage.kt) and there is also bot's [implementation](../bot/src/main/kotlin/me/y9san9/prizebot/database/states_storage/TableStorage.kt) with database.
- [FSMContextManager](src/main/kotlin/me/y9san9/fsm/FSMContextManager.kt) - a simple wrapper around FSMStorage that caches loading results in memory. There is also FSMContext model that stores current state name and data.
- [FSMState](src/main/kotlin/me/y9san9/fsm/FSMState.kt) - an interface that represents state, see impl [example](../bot/src/main/kotlin/me/y9san9/prizebot/handlers/private_messages/fsm/states/MainState.kt).
- [FSMStateResult](src/main/kotlin/me/y9san9/fsm/FSMStateResult.kt) - state result data and next state that consumes this data
- [FSMStates](src/main/kotlin/me/y9san9/fsm/FSMStates.kt) - list of states but with main state

Also, you should know what is _channels_ in FSM: it is kind of splitters of events, so if there are only one event source (in case of android apps) it should not be used, but in telegram each user seen as a separate event source, so one user state change doesn't affect on another user state.

### How does it work
When FSM created, events flow is paralleled with [FlowParallelLauncher](../utils/src/main/kotlin/me/y9san9/extensions/flow/FlowParallelLauncher.kt) so different channel events runs in parallel but one user events runs consistently.

Then, after event came, it gets current state name from ContextManager, selects a state matches the name, processes event with the state and saving next state name and data with ContextManager

See [sources](src/main/kotlin/me/y9san9/fsm/FSM.kt).
