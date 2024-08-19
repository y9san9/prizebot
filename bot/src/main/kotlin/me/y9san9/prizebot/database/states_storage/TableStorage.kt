package me.y9san9.prizebot.database.states_storage

import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.modules.SerializersModule
import me.y9san9.fsm.storage.SerializableStorage
import me.y9san9.prizebot.database.states_storage.TableStorage.StatesTable.channel
import me.y9san9.prizebot.database.states_storage.TableStorage.StatesTable.data
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
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

    override suspend fun saveData(key: String, state: String) = newSuspendedTransaction(Dispatchers.IO, database) {
        if(StatesTable.selectAll().where { channel eq key.toLong() }.firstOrNull() == null) {
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

    override suspend fun loadData(key: String): String? = newSuspendedTransaction(Dispatchers.IO, database) {
        StatesTable.selectAll().where { channel eq key.toLong() }.firstOrNull()?.get(data)
    }
}
