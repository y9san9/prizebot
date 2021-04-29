package me.y9san9.prizebot.database.user_titles_storage

import org.jetbrains.exposed.sql.Database


fun UserTitlesStorage(database: Database): UserTitlesStorage = TableUserTitlesStorage(database)

interface UserTitlesStorage {
    fun saveUserTitle(userId: Long, title: String)
    fun getUserTitle(userId: Long): String?
}
