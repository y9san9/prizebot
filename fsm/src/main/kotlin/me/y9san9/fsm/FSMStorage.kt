package me.y9san9.fsm


interface FSMStorage <TChannel, TData> {
    suspend fun saveData(key: TChannel, data: FSMContext<TData>)
    suspend fun loadData(key: TChannel): FSMContext<TData>?
}
