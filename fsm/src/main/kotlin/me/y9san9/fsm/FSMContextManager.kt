package me.y9san9.fsm

import me.y9san9.fsm.storage.MemoryStorage


data class FSMContext <out T> (
    val name: String,
    val data: T
)

internal class FSMContextManager<TChannel> (
    private val initialName: String,
    private val storage: FSMStorage<TChannel, Any?> = MemoryStorage()
) {
    private val inMemoryContexts = mutableMapOf<TChannel, FSMContext<*>>()

    private suspend fun loadState(key: TChannel) =
        storage.loadData(key)
            ?: FSMContext (
                name = initialName,
                data = null
            )

    suspend fun get(key: TChannel): FSMContext<*> = inMemoryContexts.getOrPut(key) { loadState(key) }
    suspend fun set(key: TChannel, state: FSMContext<*>) {
        inMemoryContexts[key] = state
        storage.saveData(key, state)
    }
}
