package me.y9san9.fsm


class FSMStates <TEvent> (
    val initial: FSMState<Unit, TEvent>,
    states: List<FSMState<*, TEvent>>
) {
    val states = states + initial
}

fun <TEvent> statesOf(initial: FSMState<Unit, TEvent>, vararg states: FSMState<*, TEvent>) =
    FSMStates(initial, states.toList())
