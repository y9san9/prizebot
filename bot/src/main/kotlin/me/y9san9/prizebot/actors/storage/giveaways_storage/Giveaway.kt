package me.y9san9.prizebot.actors.storage.giveaways_storage

import kotlinx.serialization.Serializable
import me.y9san9.prizebot.resources.locales.Locale


@Serializable
sealed class Giveaway {
    abstract val id: Long
    abstract val ownerId: Long
    abstract val title: String
    abstract val participateText: String
    abstract val languageCode: String?
}

val Giveaway.locale get() = Locale.with(languageCode)

@Serializable
data class ActiveGiveaway (
    override val id: Long,
    override val ownerId: Long,
    override val title: String,
    override val participateText: String,
    override val languageCode: String?
) : Giveaway()

@Serializable
data class FinishedGiveaway (
    override val id: Long,
    override val ownerId: Long,
    override val title: String,
    override val participateText: String,
    override val languageCode: String?,
    val winnerId: Long
) : Giveaway()
