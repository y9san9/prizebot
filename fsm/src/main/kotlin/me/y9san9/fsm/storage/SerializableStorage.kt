@file:Suppress("EXPERIMENTAL_API_USAGE")

package me.y9san9.fsm.storage

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import kotlinx.serialization.modules.polymorphic
import me.y9san9.fsm.FSMContext
import me.y9san9.fsm.FSMStorage


@Serializable
@SerialName("long")
data class LongWrapper (
    val value: Long
)

@Serializable
private data class SerializedData (
    val name: String,
    @Polymorphic
    val data: Any?
)

abstract class SerializableStorage <TChannel : Any, TData> (
    serializers: SerializersModule
) : FSMStorage<TChannel, TData> {

    private val json = Json {
        serializersModule = SerializersModule {
            polymorphic(Any::class) {
                subclass(LongWrapper::class, LongWrapper.serializer())
            }
        }
        serializersModule += serializers
    }

    final override suspend fun saveData(key: @Contextual TChannel, data: FSMContext<TData>) {
        val serializer = json.serializersModule.getContextual(key::class) ?: error("No contextual serializer for key found")

        @Suppress("UNCHECKED_CAST")
        val serializedKeyEncoded = json.encodeToString(serializer as SerializationStrategy<TChannel>, key)

        val serializedData = SerializedData(data.name, data.data as Any?)
        val serializedDataEncoded = json.encodeToString(serializedData)

        saveData(serializedKeyEncoded, serializedDataEncoded)
    }

    final override suspend fun loadData(key: TChannel): FSMContext<TData>? {
        val serializer = json.serializersModule.getContextual(key::class) ?: error("No contextual serializer for key found")
        @Suppress("UNCHECKED_CAST")
        val serializedKeyEncoded = json.encodeToString(serializer as SerializationStrategy<TChannel>, key)

        val serializedDataEncoded = loadData(serializedKeyEncoded) ?: return null
        val serializedData = json.decodeFromString<SerializedData>(serializedDataEncoded)

        @Suppress("UNCHECKED_CAST")
        return FSMContext(serializedData.name, serializedData.data as TData)
    }

    abstract suspend fun saveData(key: String, state: String)
    abstract suspend fun loadData(key: String): String?
}
