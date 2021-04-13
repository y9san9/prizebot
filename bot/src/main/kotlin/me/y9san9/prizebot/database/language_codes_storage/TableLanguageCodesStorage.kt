package me.y9san9.prizebot.database.language_codes_storage

import me.y9san9.prizebot.database.language_codes_storage.TableLanguageCodesStorage.Storage.LANGUAGE_CODE
import me.y9san9.prizebot.database.language_codes_storage.TableLanguageCodesStorage.Storage.USER_ID
import me.y9san9.prizebot.extensions.any.unit
import org.jetbrains.exposed.sql.*
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

    override fun setLanguageCode(userId: Long, languageCode: String) = transaction(database) {
        Storage.deleteWhere { USER_ID eq userId }
        Storage.insert {
            it[USER_ID] = userId
            it[LANGUAGE_CODE] = languageCode
        }
    }.unit

    override fun getLanguageCode(userId: Long) = transaction(database) {
        Storage.select { USER_ID eq userId }.firstOrNull()?.get(LANGUAGE_CODE)
    }

    override fun containsLanguageCode(userId: Long) = transaction(database) {
        Storage.select { USER_ID eq userId }.firstOrNull() != null
    }
}
