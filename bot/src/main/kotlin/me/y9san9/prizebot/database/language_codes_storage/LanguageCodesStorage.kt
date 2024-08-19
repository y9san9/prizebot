package me.y9san9.prizebot.database.language_codes_storage

import org.jetbrains.exposed.sql.Database


fun LanguageCodesStorage(database: Database): LanguageCodesStorage =
    TableLanguageCodesStorage(database)

interface LanguageCodesStorage {
    suspend fun setLanguageCode(userId: Long, languageCode: String)
    suspend fun getLanguageCode(userId: Long): String?
    suspend fun containsLanguageCode(userId: Long): Boolean
}
