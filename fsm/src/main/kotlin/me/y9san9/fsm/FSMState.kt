package me.y9san9.fsm


interface FSMState<TDataIn, TEvent> {
    val name: String get() = this::class.simpleName ?: "noname"

    suspend fun process(data: TDataIn, event: TEvent): FSMStateResult<*>
}
