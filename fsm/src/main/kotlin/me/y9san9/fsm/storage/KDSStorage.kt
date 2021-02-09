package me.y9san9.fsm.storage

import com.kotlingang.kds.KDataStorage
import com.kotlingang.kds.StorageConfigBuilder
import com.kotlingang.kds.mutate
import kotlinx.serialization.modules.SerializersModule


private class KDS : KDataStorage {
    constructor(name: String) : super(name)
    constructor(builder: StorageConfigBuilder) : super(builder)

    val states by property(mutableMapOf<String, String>())
}

class KDSStorage<TChannel : Any, TData> : SerializableStorage<TChannel, TData> {

    private val kds: KDS

    constructor(serializers: SerializersModule, name: String = "states") : super(serializers) {
        kds = KDS(name)
    }
    constructor(serializers: SerializersModule, builder: StorageConfigBuilder) : super(serializers) {
        kds = KDS(builder)
    }

    override suspend fun saveData(key: String, state: String) = kds.mutate {
        states[key] = state
    }
    override suspend fun loadData(key: String): String? {
        return kds.states[key]
    }

}
