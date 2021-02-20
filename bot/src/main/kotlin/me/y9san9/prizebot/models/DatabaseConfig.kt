package me.y9san9.prizebot.models


data class DatabaseConfig (
    val url: String,
    val user: String,
    val password: String,
    val driver: String?
)
