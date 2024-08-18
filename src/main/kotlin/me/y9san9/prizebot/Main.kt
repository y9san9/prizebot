package me.y9san9.prizebot

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.plus
import me.y9san9.extensions.any.unit

suspend fun main() = coroutineScope {
    val token = System.getenv("BOT_TOKEN") ?: error("Provide BOT_TOKEN environment variable")
    val randomOrgApiKey = System.getenv("RANDOM_ORG_API_KEY")
        ?: error("Please visit api.random.org and provide RANDOM_ORG_API_KEY environment variable")

    val logChatId = System.getenv("LOG_CHAT_ID")?.toLongOrNull()

    val databaseConfig = run {
        DatabaseConfig.Actual(
            url = System.getenv("DATABASE_URL")
                ?: return@run null,
            user = System.getenv("DATABASE_USER")
                ?: return@run null,
            password = System.getenv("DATABASE_PASSWORD")
                ?: return@run null,
            driver = System.getenv("DATABASE_DRIVER")
        )
    } ?: DatabaseConfig.InMemory

    System.err.println("Warning! Using in-memory database. All changes will be lost after restart. " +
        "To with database, provide DATABASE_URL, DATABASE_USER and DATABASE_PASSWORD env variables")

    Prizebot(
        botToken = token,
        randomOrgApiKey = randomOrgApiKey,
        databaseConfig = databaseConfig,
        logChatId = logChatId,
        scope = this + Dispatchers.IO
    ).start()
}.unit
