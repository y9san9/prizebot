package me.y9san9.prizebot.actors.storage.language_codes_storage

import kotlinx.serialization.Serializable


@Serializable
data class LanguageCode (
    val userId: Long,
    val code: String?
)
