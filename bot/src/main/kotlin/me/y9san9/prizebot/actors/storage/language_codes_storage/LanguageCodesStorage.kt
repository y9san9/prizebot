package me.y9san9.prizebot.actors.storage.language_codes_storage

import org.jetbrains.exposed.sql.Database


interface LanguageCodesStorage {
    fun setLanguageCode(userId: Long, languageCode: String)
    fun getLanguageCode(userId: Long): String?
    fun containsLanguageCode(userId: Long): Boolean
}


@Suppress("FunctionName")
fun LanguageCodesStorage(database: Database?) =
    if(database == null)
        KDSLanguageCodesStorage()
    else TableLanguageCodesStorage(database)
