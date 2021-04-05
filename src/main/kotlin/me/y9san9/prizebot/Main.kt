package me.y9san9.prizebot

import kotlinx.coroutines.coroutineScope
import me.y9san9.prizebot.extensions.any.unit
import me.y9san9.prizebot.models.DatabaseConfig


suspend fun main() = coroutineScope {
    val token = System.getenv("BOT_TOKEN") ?: error("Provide BOT_TOKEN environment variable")
    val logChatId = System.getenv("LOG_CHAT_ID")?.toLongOrNull()

    val databaseConfig = getDatabaseConfig (
        url = System.getenv("DATABASE_URL"),
        user = System.getenv("DATABASE_USER"),
        password = System.getenv("DATABASE_PASSWORD"),
        driver = System.getenv("DATABASE_DRIVER")
    )

    Prizebot (
        botToken = token,
        databaseConfig = databaseConfig,
        logChatId = logChatId,
        scope = this
    ).start()
}.unit


private fun getDatabaseConfig (
    url: String?, user: String?,
    password: String?, driver: String?
): DatabaseConfig? {
    val data = arrayOf(url, user, password)
    return when {
        data.all { it != null } -> DatabaseConfig(url!!, user!!, password!!, driver)
        data.any { it != null } -> error("Only particular data for database connecting provided, please follow README guide")
        else -> null
    }
}
