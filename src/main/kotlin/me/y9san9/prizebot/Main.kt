package me.y9san9.prizebot

import kotlinx.coroutines.coroutineScope
import me.y9san9.prizebot.models.DatabaseConfig


suspend fun main() = coroutineScope {
    val token = System.getenv("BOT_TOKEN") ?: error("Provide BOT_TOKEN environment variable")

    val databaseUrl = System.getenv("DATABASE_URL")
    val databaseUser = System.getenv("DATABASE_USER")
    val databasePassword = System.getenv("DATABASE_PASSWORD")
    val databaseDriver = System.getenv("DATABASE_DRIVER")

    val data = arrayOf(databaseUrl, databaseUser, databasePassword)

    val databaseConfig = when {
        data.all { it != null } -> DatabaseConfig(databaseUrl, databaseUser, databasePassword, databaseDriver)
        data.any { it != null } -> error("Only particular data for database connecting entered, please follow README guide")
        else -> null
    }

    Prizebot (
        botToken = token,
        databaseConfig = databaseConfig,
        scope = this
    ).start()

    return@coroutineScope
}
