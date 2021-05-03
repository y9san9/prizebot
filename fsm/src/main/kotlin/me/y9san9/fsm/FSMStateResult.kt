package me.y9san9.fsm


data class FSMStateResult <TDataOut> (
    val data: TDataOut,
    // next state should consume output of this state
    val nextState: FSMState<TDataOut, *>
)

inline fun <T> stateResult(nextState: FSMState<T, *>, data: T, apply: () -> Unit = {}) =
    FSMStateResult (
        data = data,
        nextState = nextState
    ).apply { apply() }

inline fun stateResult(nextState: FSMState<Unit, *>, apply: () -> Unit = {}) =
    stateResult(nextState, data = Unit, apply)
