package me.y9san9.prizebot.actors.storage.giveaways_storage

import me.y9san9.prizebot.resources.locales.Locale
import java.time.OffsetDateTime


inline class WinnersCount(val value: Int)  {
    init {
        require(value in 1..50_000) { "Winners count is out of range" }
    }
}

sealed class Giveaway {
    abstract val id: Long
    abstract val ownerId: Long
    abstract val title: String
    abstract val participateText: String
    abstract val languageCode: String?
    abstract val raffleDate: OffsetDateTime?
}

data class ActiveGiveaway (
    override val id: Long,
    override val ownerId: Long,
    override val title: String,
    override val participateText: String,
    override val languageCode: String?,
    override val raffleDate: OffsetDateTime?,
    val winnersCount: WinnersCount
) : Giveaway()

data class FinishedGiveaway (
    override val id: Long,
    override val ownerId: Long,
    override val title: String,
    override val participateText: String,
    override val languageCode: String?,
    override val raffleDate: OffsetDateTime?,
    val winnerIds: List<Long>
) : Giveaway()


val Giveaway.locale get() = Locale.with(languageCode)
