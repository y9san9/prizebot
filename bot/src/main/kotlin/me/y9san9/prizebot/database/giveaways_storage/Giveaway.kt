package me.y9san9.prizebot.database.giveaways_storage

import me.y9san9.prizebot.actors.giveaway.AutoRaffleActor
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.ConditionsStorage
import me.y9san9.prizebot.database.giveaways_storage.giveaways_patch_storage.GiveawaysPatchStorage
import me.y9san9.prizebot.database.giveaways_storage.participants_storage.ParticipantsStorage
import me.y9san9.prizebot.database.giveaways_storage.winners_storage.WinnersStorage
import me.y9san9.prizebot.resources.locales.Locale
import java.time.OffsetDateTime


sealed class Giveaway {
    abstract val id: Long
    abstract val ownerId: Long
    abstract val title: String
    abstract val participateText: String
    abstract val languageCode: String?
    abstract val raffleDate: OffsetDateTime?
    protected abstract val winnersSettings: WinnersSettings

    /* Participants composition */

    internal abstract val participantsStorage: ParticipantsStorage

    suspend fun getParticipantsIds(): List<Long> = participantsStorage.getParticipantsIds(id)
    suspend fun getParticipantsCount(): Int = participantsStorage.getParticipantsCount(id)

    suspend fun isParticipant(userId: Long) = participantsStorage.isParticipant(id, userId)
    suspend fun saveParticipant(userId: Long) = participantsStorage.saveParticipant(id, userId)
    suspend fun removeParticipant(userId: Long) = participantsStorage.removeParticipant(id, userId)

    /* Patch composition */

    internal abstract val giveawaysPatchStorage: GiveawaysPatchStorage

    suspend fun delete() = giveawaysPatchStorage.deleteGiveaway(id)

    /* Conditions composition */

    internal abstract val conditionsStorage: ConditionsStorage

    suspend fun loadConditions() = conditionsStorage.loadConditions(id)
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
    override val conditionsStorage: ConditionsStorage,
    override val winnersSettings: WinnersSettings,
    val raffleActor: AutoRaffleActor
) : Giveaway() {
    val winnersCount = winnersSettings.winnersCount
    suspend fun removeRaffleDate() = giveawaysPatchStorage.removeRaffleDate(id)
    suspend fun finish(winnerIds: List<Long>) {
        giveawaysPatchStorage.finishGiveaway(id, winnerIds)
        giveawaysPatchStorage.removeRaffleDate(id)
        raffleActor.cancelSchedulesRaffle(id)
    }
}

data class FinishedGiveaway internal constructor (
    override val id: Long,
    override val ownerId: Long,
    override val title: String,
    override val participateText: String,
    override val languageCode: String?,
    override val raffleDate: OffsetDateTime?,
    override val winnersSettings: WinnersSettings,
    override val participantsStorage: ParticipantsStorage,
    override val giveawaysPatchStorage: GiveawaysPatchStorage,
    override val conditionsStorage: ConditionsStorage,
    private val winnersStorage: WinnersStorage
) : Giveaway() {
    suspend fun getWinners() = winnersStorage.getWinners(id)
    val displayWinnersWithEmojis = winnersSettings.displayWithEmojis
}


val Giveaway.locale get() = Locale.with(languageCode)
