package me.y9san9.fsm


interface FSMState<TDataIn, TEvent> {
    val name: String get() = this::class.simpleName ?: "noname"

    suspend fun process(data: TDataIn, event: TEvent): FSMStateResult<*>
}


inline fun <TDataIn, TEvent> context (
    name: String,
    crossinline processor: suspend (data: TDataIn, event: TEvent) -> FSMStateResult<*>
) = object : FSMState<TDataIn, TEvent> {
    override val name = name

    override suspend fun process(data: TDataIn, event: TEvent) = processor(data, event)
}
