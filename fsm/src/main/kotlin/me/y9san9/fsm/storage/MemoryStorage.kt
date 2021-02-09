package me.y9san9.fsm.storage

import me.y9san9.fsm.FSMContext
import me.y9san9.fsm.FSMContextManager
import me.y9san9.fsm.FSMStorage


/**
 * Simplest storage for store data in memory.
 * (This behaviour by default implemented in [FSMContextManager] so this implementation does nothing)
 */
class MemoryStorage<TChannel, TData> : FSMStorage<TChannel, TData> {
    override suspend fun saveData(key: TChannel, data: FSMContext<TData>) = Unit
    override suspend fun loadData(key: TChannel): FSMContext<TData>? = null
}
