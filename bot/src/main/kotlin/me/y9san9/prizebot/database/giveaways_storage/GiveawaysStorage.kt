package me.y9san9.prizebot.database.giveaways_storage

import me.y9san9.prizebot.actors.giveaway.AutoRaffleActor
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.GiveawayConditions
import org.jetbrains.exposed.sql.Database
import java.time.OffsetDateTime


fun GiveawaysStorage(
    database: Database,
    autoRaffleActor: AutoRaffleActor
): GiveawaysStorage = TableGiveawaysStorage(database, autoRaffleActor)

interface GiveawaysStorage {
    suspend fun getGiveawayById(id: Long): Giveaway?
    suspend fun saveGiveaway (
        ownerId: Long,
        title: String,
        participateButton: String,
        languageCode: String?,
        raffleDate: OffsetDateTime?,
        winnersSettings: WinnersSettings,
        conditions: GiveawayConditions
    ): ActiveGiveaway
    suspend fun getUserGiveaways(ownerId: Long, count: Int = 20, offset: Long = 0): List<Giveaway>
    suspend fun getAllGiveaways(): List<Giveaway>
}
