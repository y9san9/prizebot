package me.y9san9.fsm


data class FSMStateResult <TDataOut> (
    val data: TDataOut,
    // next state should consume output of this state
    val nextState: FSMState<TDataOut, *>
)

fun <T> result(data: T, nextState: FSMState<T, *>) =
    FSMStateResult (
        data = data,
        nextState = nextState
    )

fun result(nextState: FSMState<Unit, *>) = result(data = Unit, nextState)
