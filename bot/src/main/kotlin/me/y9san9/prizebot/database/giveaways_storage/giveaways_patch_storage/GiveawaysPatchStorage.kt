package me.y9san9.prizebot.database.giveaways_storage.giveaways_patch_storage

import me.y9san9.prizebot.database.giveaways_storage.winners_storage.WinnersStorage
import org.jetbrains.exposed.sql.Database


internal fun GiveawaysPatchStorage(database: Database, winnersStorage: WinnersStorage): GiveawaysPatchStorage =
    TableGiveawaysPatchStorage(database, winnersStorage)


internal interface GiveawaysPatchStorage {
    suspend fun finishGiveaway(giveawayId: Long, winnerIds: List<Long>)
    suspend fun removeRaffleDate(giveawayId: Long)
    suspend fun deleteGiveaway(id: Long)
}