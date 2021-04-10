package me.y9san9.prizebot.actors.storage.giveaways_storage

import org.jetbrains.exposed.sql.Database
import java.time.OffsetDateTime


fun GiveawaysStorage(database: Database): GiveawaysStorage = TableGiveawaysStorage(database)

interface GiveawaysStorage {
    fun getGiveawayById(id: Long): Giveaway?
    fun saveGiveaway (
        ownerId: Long,
        title: String,
        participateButton: String,
        languageCode: String?,
        raffleDate: OffsetDateTime?,
        winnersCount: WinnersCount
    ): ActiveGiveaway
    fun finishGiveaway(giveawayId: Long, winnerIds: List<Long>)
    fun removeRaffleDate(giveawayId: Long)
    fun getUserGiveaways(ownerId: Long, count: Int = 20, offset: Long = 0): List<Giveaway>
    fun getAllGiveaways(): List<Giveaway>
    fun deleteGiveaway(id: Long)
}
