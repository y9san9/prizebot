package me.y9san9.prizebot.database.giveaways_storage

import me.y9san9.prizebot.database.giveaways_storage.giveaways_patch_storage.GiveawaysPatchStorage
import me.y9san9.prizebot.database.participants_storage.ParticipantsStorage
import me.y9san9.prizebot.database.winners_storage.WinnersStorage
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

    /* Participants composition */

    internal abstract val participantsStorage: ParticipantsStorage

    val participants by lazy { participantsStorage.getParticipantsIds(id) }
    val participantsCount by lazy { participantsStorage.getParticipantsCount(id) }

    fun isParticipant(userId: Long) = participantsStorage.isParticipant(id, userId)
    fun saveParticipant(userId: Long) = participantsStorage.saveParticipant(id, userId)

    /* Patch composition */

    internal abstract val giveawaysPatchStorage: GiveawaysPatchStorage

    fun delete() = giveawaysPatchStorage.deleteGiveaway(id)
}

data class ActiveGiveaway internal constructor (
    override val id: Long,
    override val ownerId: Long,
    override val title: String,
    override val participateText: String,
    override val languageCode: String?,
    override val raffleDate: OffsetDateTime?,
    override val participantsStorage: ParticipantsStorage,
    override val giveawaysPatchStorage: GiveawaysPatchStorage,
    val winnersCount: WinnersCount
) : Giveaway() {
    fun removeRaffleDate() = giveawaysPatchStorage.removeRaffleDate(id)
    fun finish(winnerIds: List<Long>) = giveawaysPatchStorage.finishGiveaway(id, winnerIds)
}

data class FinishedGiveaway internal constructor (
    override val id: Long,
    override val ownerId: Long,
    override val title: String,
    override val participateText: String,
    override val languageCode: String?,
    override val raffleDate: OffsetDateTime?,
    override val participantsStorage: ParticipantsStorage,
    override val giveawaysPatchStorage: GiveawaysPatchStorage,
    private val winnersStorage: WinnersStorage
) : Giveaway() {
    val winnerIds by lazy { winnersStorage.getWinners(id) }
}


val Giveaway.locale get() = Locale.with(languageCode)
