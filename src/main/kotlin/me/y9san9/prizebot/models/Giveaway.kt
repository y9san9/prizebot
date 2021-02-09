package me.y9san9.prizebot.models

import kotlinx.serialization.Serializable
import me.y9san9.prizebot.resources.locales.Locale


@Serializable
sealed class Giveaway {
    abstract val id: Long
    abstract val ownerId: Long
    abstract val title: String
    abstract val participateButton: String
    abstract val languageCode: String?
}

val Giveaway.locale get() = Locale.with(languageCode)

@Serializable
data class ActiveGiveaway (
    override val id: Long,
    override val ownerId: Long,
    override val title: String,
    override val participateButton: String,
    override val languageCode: String?
) : Giveaway()

@Serializable
data class FinishedGiveaway (
    override val id: Long,
    override val ownerId: Long,
    override val title: String,
    override val participateButton: String,
    override val languageCode: String?,
    val winnerId: Long
) : Giveaway()
