package me.y9san9.prizebot.database.language_codes_storage

import kotlinx.coroutines.Dispatchers
import me.y9san9.prizebot.database.language_codes_storage.TableLanguageCodesStorage.Storage.LANGUAGE_CODE
import me.y9san9.prizebot.database.language_codes_storage.TableLanguageCodesStorage.Storage.USER_ID
import me.y9san9.extensions.any.unit
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


internal class TableLanguageCodesStorage(private val database: Database) : LanguageCodesStorage {
    private object Storage : Table(name = "language_codes") {
        val USER_ID = long("userId")
        val LANGUAGE_CODE = text("languageCode")
    }

    init {
        transaction(database) {
            SchemaUtils.create(Storage)
        }
    }

    override suspend fun setLanguageCode(userId: Long, languageCode: String) = newSuspendedTransaction(Dispatchers.IO, database) {
        Storage.deleteWhere { USER_ID eq userId }
        Storage.insert {
            it[USER_ID] = userId
            it[LANGUAGE_CODE] = languageCode
        }
    }.unit

    override suspend fun getLanguageCode(userId: Long) = newSuspendedTransaction(Dispatchers.IO, database) {
        Storage.selectAll().where { USER_ID eq userId }.firstOrNull()?.get(LANGUAGE_CODE)
    }

    override suspend fun containsLanguageCode(userId: Long) = newSuspendedTransaction(Dispatchers.IO, database) {
        Storage.selectAll().where { USER_ID eq userId }.firstOrNull() != null
    }
}
