package me.y9san9.prizebot

import kotlinx.coroutines.coroutineScope
import me.y9san9.extensions.any.unit


suspend fun main() = coroutineScope {
    val token = System.getenv("BOT_TOKEN") ?: error("Provide BOT_TOKEN environment variable")
    val logChatId = System.getenv("LOG_CHAT_ID")?.toLongOrNull()

    val databaseConfig = DatabaseConfig (
        url = System.getenv("DATABASE_URL")
            ?: error("Provide DATABASE_URL environment variable"),
        user = System.getenv("DATABASE_USER")
            ?: error("Provide DATABASE_USER environment variable"),
        password = System.getenv("DATABASE_PASSWORD")
            ?: error("Provide DATABASE_PASSWORD environment variable"),
        driver = System.getenv("DATABASE_DRIVER")
    )

    Prizebot (
        botToken = token,
        databaseConfig = databaseConfig,
        logChatId = logChatId,
        scope = this
    ).start()
}.unit
