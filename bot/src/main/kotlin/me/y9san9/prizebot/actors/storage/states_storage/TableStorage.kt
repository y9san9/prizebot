package me.y9san9.prizebot.actors.storage.states_storage

import kotlinx.serialization.modules.SerializersModule
import me.y9san9.fsm.storage.SerializableStorage
import me.y9san9.prizebot.actors.storage.states_storage.TableStorage.StatesTable.channel
import me.y9san9.prizebot.actors.storage.states_storage.TableStorage.StatesTable.data
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


internal class TableStorage <TData> (
    private val database: Database,
    serializers: SerializersModule
) : SerializableStorage<Long, TData>(serializers) {

    private object StatesTable : Table(name = "states") {
        val channel = long("channel")
        val data = text("data")
    }

    init {
        transaction(database) {
            SchemaUtils.create(StatesTable)
        }
    }

    override suspend fun saveData(key: String, state: String) = transaction(database) {
        if(StatesTable.select { channel eq key.toLong() }.firstOrNull() == null) {
            StatesTable.insert {
                it[channel] = key.toLong()
                it[data] = state
            }
        } else {
            StatesTable.update({ channel eq key.toLong() }) {
                it[data] = state
            }
        }
    }.let { }

    override suspend fun loadData(key: String): String? = transaction(database) {
        StatesTable.select { channel eq key.toLong() }.firstOrNull()?.get(data)
    }
}
